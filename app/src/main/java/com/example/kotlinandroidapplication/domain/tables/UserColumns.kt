package com.example.kotlinandroidapplication.domain.tables

import android.provider.BaseColumns

object UserColumns : BaseColumns, AbstractTable {
    const val TABLE_NAME = "users"
    const val COLUMN_NAME_LOGIN = "login"
    const val COLUMN_NAME_PASSWORD = "password"
    const val COLUMN_NAME_EMAIL = "email"
    const val COLUMN_NAME_LAST_AUTHORIZED = "last_authorized"

    const val SQL_CREATE_USERS = "CREATE TABLE" +
            " $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " $COLUMN_NAME_LOGIN TEXT," +
            " $COLUMN_NAME_PASSWORD TEXT," +
            " $COLUMN_NAME_EMAIL TEXT," +
            " $COLUMN_NAME_LAST_AUTHORIZED TEXT)"

    override fun getTableName(): String {
        return TABLE_NAME
    }
}
