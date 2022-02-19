package com.example.kotlinandroidapplication.domain.tables

import android.provider.BaseColumns

object CommentColumns : BaseColumns, AbstractTable {
    const val TABLE_NAME = "comments"
    const val COLUMN_NAME_TEXT = "text"
    const val COLUMN_NAME_PRODUCT_ID = "product_id"
    const val COLUMN_NAME_USER_ID = "user_id"

    const val SQL_CREATE_COMMENTS = "CREATE TABLE" +
            " $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " $COLUMN_NAME_TEXT TEXT," +
            " $COLUMN_NAME_PRODUCT_ID INTEGER," +
            " $COLUMN_NAME_USER_ID INTEGER)"

    override fun getTableName(): String {
        return TABLE_NAME
    }
}
