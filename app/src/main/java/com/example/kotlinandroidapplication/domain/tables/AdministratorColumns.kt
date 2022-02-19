package com.example.kotlinandroidapplication.domain.tables

import android.provider.BaseColumns

object AdministratorColumns : BaseColumns, AbstractTable {
    const val TABLE_NAME = "administrators"
    const val COLUMN_NAME_LOGIN = "login"
    const val COLUMN_NAME_PASSWORD = "password"
    const val COLUMN_NAME_LAST_AUTHORIZED = "lastAuthorized"

    const val SQL_CREATE_ADMINISTRATORS = "CREATE TABLE" +
            " $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " $COLUMN_NAME_LOGIN TEXT," +
            " $COLUMN_NAME_PASSWORD TEXT," +
            " $COLUMN_NAME_LAST_AUTHORIZED TEXT)"

    override fun getTableName(): String {
        return TABLE_NAME
    }
}
