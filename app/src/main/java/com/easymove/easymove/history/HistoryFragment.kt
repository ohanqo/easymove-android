package com.easymove.easymove.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.easymove.easymove.R
import com.easymove.easymove.shared.extensions.setIsVisible
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private val adapter = HistoryAdapter()
    private val model: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        history_recycler.adapter = adapter

        lifecycleScope.launch {
            model.historyItems.collect {
                adapter.submitData(it)
                
                val hasData = adapter.itemCount > 0
                history_list.setIsVisible(hasData)
                history_no_data.setIsVisible(!hasData)
            }
        }
    }
}