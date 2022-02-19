package com.example.kotlinandroidapplication.domain

import android.content.ContentValues
import com.example.kotlinandroidapplication.domain.tables.CommentColumns

data class Comment(
    var id: Long?,
    var text: String?,
    var productId: Long?,
    var userId: Long?
) {
    fun asValues(): ContentValues {
        return ContentValues().apply {
            put(CommentColumns.COLUMN_NAME_TEXT, text)
            put(CommentColumns.COLUMN_NAME_PRODUCT_ID, productId)
            put(CommentColumns.COLUMN_NAME_USER_ID, userId)
        }
    }
}
