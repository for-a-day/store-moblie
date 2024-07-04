package com.nagane.table.data.table

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun fromNumber(value: Number?): Double? {
        return value?.toDouble()
    }

    @TypeConverter
    fun toNumber(value: Double?): Number? {
        return value
    }
}