package com.easymove.easymove.shared.extensions

import android.os.SystemClock
import android.view.View

fun View.setOnSingleClickListener(debounceTime: Long = 200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
