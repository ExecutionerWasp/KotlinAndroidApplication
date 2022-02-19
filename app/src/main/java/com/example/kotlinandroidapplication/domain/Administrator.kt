package com.example.kotlinandroidapplication.domain

import android.content.ContentValues
import com.example.kotlinandroidapplication.domain.tables.AdministratorColumns
import java.time.LocalDateTime

data class Administrator(
    var id: Long?,
    var login: String,
    var password: String,
    var lastAuthorized: LocalDateTime?
){
    constructor() : this(null, "admin", "admin", null)

    fun asValues(): ContentValues {
        return ContentValues().apply {
            put(AdministratorColumns.COLUMN_NAME_LOGIN, login)
            put(AdministratorColumns.COLUMN_NAME_PASSWORD, password)
            put(AdministratorColumns.COLUMN_NAME_LAST_AUTHORIZED, lastAuthorized.toString())
        }
    }
}
