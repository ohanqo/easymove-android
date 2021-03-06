package com.easymove.easymove.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.easymove.easymove.R
import com.easymove.easymove.shared.utils.TimeUtils
import kotlinx.android.synthetic.main.item_history.view.*


class HistoryAdapter : PagingDataAdapter<History, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(history: History) {
            itemView.apply {
                history_date_item.text = TimeUtils.formatDay(history.createdAt)
                history_start_item.text = history.departureStation
                history_time_item.text = TimeUtils.formatHour(history.createdAt)
                history_price_item.text = history.price
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }
        }
    }
}