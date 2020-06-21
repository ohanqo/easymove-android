package com.easymove.easymove.shared.utils

import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.easymove.easymove.R

class ToastUtils {
    companion object {
        fun error(context: Context, message: String) {
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            val view: View = toast.view
            val textColor = ContextCompat.getColor(context, android.R.color.white)
            val backgroundColor = ContextCompat.getColor(context, R.color.red_500)
            val text: TextView = (view as LinearLayout).getChildAt(0) as TextView
            val typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)

            view.background.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN)
            text.setTextColor(textColor)
            text.typeface = typeface

            toast.show()
        }
    }
}
