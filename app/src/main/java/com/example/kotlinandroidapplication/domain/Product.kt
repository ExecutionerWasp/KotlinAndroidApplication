package com.example.kotlinandroidapplication.domain

import android.content.ContentValues
import com.example.kotlinandroidapplication.domain.tables.ProductColumns

data class Product(
    var id: Long?,
    var name:String?,
    var price:Double?,
    var description:String?,
    var imgUrl:String?,
    var authorId: Long?
) {
    constructor() : this(null, null, null, null, null, null)

    fun asValues(): ContentValues {
        return ContentValues().apply {
            put(ProductColumns.COLUMN_NAME_NAME, name)
            put(ProductColumns.COLUMN_NAME_PRICE, price)
            put(ProductColumns.COLUMN_NAME_DESCRIPTION, description)
            put(ProductColumns.COLUMN_NAME_IMG_URL, imgUrl)
            put(ProductColumns.COLUMN_NAME_AUTHOR_ID, authorId)
        }
    }
}
