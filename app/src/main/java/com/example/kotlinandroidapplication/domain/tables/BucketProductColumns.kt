package com.example.kotlinandroidapplication.domain.tables

import android.provider.BaseColumns

object BucketProductColumns : BaseColumns, AbstractTable {
    const val TABLE_NAME = "buckets_products"
    const val COLUMN_NAME_BUCKET_ID = "bucket_id"
    const val COLUMN_NAME_PRODUCT_ID = "product_id"

    const val SQL_CREATE_BUCKET_PRODUCT = "CREATE TABLE" +
            " $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " $COLUMN_NAME_BUCKET_ID INTEGER," +
            " $COLUMN_NAME_PRODUCT_ID INTEGER)"

    override fun getTableName(): String {
        return TABLE_NAME
    }
}
