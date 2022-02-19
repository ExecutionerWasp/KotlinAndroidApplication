package com.example.kotlinandroidapplication.domain

import android.content.ContentValues
import com.example.kotlinandroidapplication.domain.tables.UserColumns
import java.time.LocalDateTime

data class User(
    var id: Long?,
    var login: String?,
    var password: String?,
    var email: String?,
    var lastAuthorized: LocalDateTime?
) {
    constructor() : this(null, null,null,null, null)

    fun asValues(): ContentValues {
        return ContentValues().apply {
            put(UserColumns.COLUMN_NAME_LOGIN, login)
            put(UserColumns.COLUMN_NAME_PASSWORD, password)
            put(UserColumns.COLUMN_NAME_EMAIL, email)
            put(UserColumns.COLUMN_NAME_LAST_AUTHORIZED, lastAuthorized.toString())
        }
    }

}
