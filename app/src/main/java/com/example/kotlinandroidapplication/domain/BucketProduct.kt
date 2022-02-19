package com.example.kotlinandroidapplication.domain

import android.content.ContentValues
import com.example.kotlinandroidapplication.domain.tables.BucketProductColumns

data class BucketProduct(
    var id: Long?,
    var bucketId: Long?,
    var productId: Long?
) {
    fun asValues(): ContentValues {
        return ContentValues().apply {
            put(BucketProductColumns.COLUMN_NAME_BUCKET_ID, bucketId)
            put(BucketProductColumns.COLUMN_NAME_PRODUCT_ID, productId)
        }
    }
}
