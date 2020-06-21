package com.easymove.easymove.shared.utils

import android.content.Context

class PrefsUtils(context: Context) {
    companion object {
        private const val PREFS_FILENAME = "com.easymove.easymove"

        const val AUTH_TOKEN = "AUTH_TOKEN"
    }

    private val prefs = context.getSharedPreferences(PREFS_FILENAME, 0)

    var authToken: String?
        get() = prefs.getString(AUTH_TOKEN, "")
        set(value) {
            if (value != null) {
                prefs.edit().putString(AUTH_TOKEN, value).apply()
            } else {
                prefs.edit().remove(AUTH_TOKEN).apply()
            }
        }
}