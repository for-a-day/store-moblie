package com.nagane.table.ui.util

import android.content.Context
import android.content.SharedPreferences
import com.nagane.table.data.model.TableLogin


object SharedPreferencesManager {
    private const val PREF_NAME = "nagane_prefs"
    private const val ACCESS_TOKEN = "access_token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val JWT_TOKEN = "jwt_token"
    private const val TABLE_CODE = "table_code"
    private const val STORE_CODE = "store_code"
    private const val TABLE_NUMBER = "table_number"
    private const val TABLE_NAME = "table_name"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveTokens(context: Context, accessToken: String, refreshToken: String) {
        val editor = getPreferences(context).edit()
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.putString(REFRESH_TOKEN, refreshToken)
        editor.apply()
    }

    fun saveAdditionalData(context: Context, tableLogin: TableLogin) {
        val editor = getPreferences(context).edit()
        editor.putString(JWT_TOKEN, "임시 토큰")
        editor.putString(TABLE_CODE, tableLogin.tableCode)
        editor.putString(STORE_CODE, tableLogin.storeCode)
        editor.putString(TABLE_NUMBER, tableLogin.tableNumber.toString())
        editor.putString(TABLE_NAME, tableLogin.tableName)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        return getPreferences(context).getString(ACCESS_TOKEN, null)
    }

    fun getRefreshToken(context: Context): String? {
        return getPreferences(context).getString(REFRESH_TOKEN, null)
    }
}