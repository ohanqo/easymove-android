package com.easymove.easymove.history

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.easymove.easymove.shared.Constants.HISTORY_FILTER_ANNUAL
import com.easymove.easymove.shared.Constants.HISTORY_FILTER_ITEMS_PER_PAGE
import com.easymove.easymove.shared.Constants.HISTORY_FILTER_START_PAGE_INDEX
import com.easymove.easymove.shared.modules.database.AppDatabase
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class HistoryPagingMediator(
    private val database: AppDatabase,
    private val service: HistoryService,
    private val historyDao: HistoryDao,
    private val remoteKeysDao: HistoryRemoteKeysDao
) : RemoteMediator<Int, History>() {
    
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, History>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            LoadType.PREPEND -> {
                val remoteKeys = remoteKeysDao.get()
                    ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                remoteKeys.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = remoteKeysDao.get()
                remoteKeys?.nextKey ?: 1
            }
        }

        try {
            when (val response = service.getHistory(HISTORY_FILTER_ANNUAL, page)) {
                is NetworkResponse.Success -> {
                    val historyItems = response.body.pageHistoryItems
                    val endOfPaginationReached = historyItems.size < HISTORY_FILTER_ITEMS_PER_PAGE
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            remoteKeysDao.delete()
                            historyDao.deleteAll()
                        }

                        val prevKey =
                            if (page == HISTORY_FILTER_START_PAGE_INDEX) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1
                        val remoteKey = HistoryRemoteKeys(prevKey = prevKey, nextKey = nextKey)

                        remoteKeysDao.insert(remoteKey)
                        historyDao.insertAll(historyItems)
                    }
                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                is NetworkResponse.ServerError -> {
                    val throwable = Throwable(response.body?.error)
                    return MediatorResult.Error(throwable)
                }
                else -> {
                    return MediatorResult.Error(Throwable("An error while loading history occurred"))
                }
            }

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}