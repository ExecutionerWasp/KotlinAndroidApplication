package com.example.kotlinandroidapplication.domain.tables

import android.provider.BaseColumns

object ProductColumns : BaseColumns, AbstractTable {
    const val TABLE_NAME = "products"
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_PRICE = "price"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_IMG_URL = "img_url"
    const val COLUMN_NAME_AUTHOR_ID = "author_id"

    const val SQL_CREATE_PRODUCTS = "CREATE TABLE" +
            " $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " $COLUMN_NAME_NAME TEXT," +
            " $COLUMN_NAME_PRICE DOUBLE," +
            " $COLUMN_NAME_DESCRIPTION TEXT," +
            " $COLUMN_NAME_IMG_URL TEXT,"+
            " $COLUMN_NAME_AUTHOR_ID INTEGER)"

    override fun getTableName(): String {
        return TABLE_NAME
    }
}
