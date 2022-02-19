package com.example.kotlinandroidapplication.domain.tables

import android.provider.BaseColumns

object BucketColumns : BaseColumns, AbstractTable {
    const val TABLE_NAME = "buckets"
    const val COLUMN_NAME_USER_ID = "user_id"
    const val COLUMN_NAME_TOTAL_PRICE = "total_price"

    const val SQL_CREATE_BUCKET = "CREATE TABLE" +
            " $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " $COLUMN_NAME_USER_ID INTEGER," +
            " $COLUMN_NAME_TOTAL_PRICE DOUBLE)"

    override fun getTableName(): String {
        return TABLE_NAME
    }
}
