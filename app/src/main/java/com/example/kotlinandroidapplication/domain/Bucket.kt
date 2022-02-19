package com.example.kotlinandroidapplication.domain

import android.content.ContentValues
import com.example.kotlinandroidapplication.domain.tables.BucketColumns

data class Bucket(
    var id: Long?,
    var userId: Long?,
    var totalPrice: Double?
) {
    fun asValues(): ContentValues {
        return ContentValues().apply {
            put(BucketColumns.COLUMN_NAME_USER_ID, userId)
            put(BucketColumns.COLUMN_NAME_TOTAL_PRICE, totalPrice)
        }
    }
}
