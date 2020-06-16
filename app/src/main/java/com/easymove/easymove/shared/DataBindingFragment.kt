package com.easymove.easymove.shared

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class DataBindingFragment : Fragment() {
    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate<T>(layoutInflater, resId, container, false).apply {
        lifecycleOwner = this@DataBindingFragment
    }
}