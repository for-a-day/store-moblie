package com.nagane.table.data.table

import androidx.databinding.BindingConversion

object DataBindingConverters {

    @BindingConversion
    @JvmStatic
    fun fromNumber(value: Number?): String {
        return value?.toString() ?: ""
    }

    @BindingConversion
    @JvmStatic
    fun toNumber(value: String?): Number? {
        return value?.toDoubleOrNull()
    }
}