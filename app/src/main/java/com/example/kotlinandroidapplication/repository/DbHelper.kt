package com.example.kotlinandroidapplication.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kotlinandroidapplication.domain.tables.*

class DbHelper(context: Context): SQLiteOpenHelper(
    context, LiteDb.DATABASE_NAME, null,
    LiteDb.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(AdministratorColumns.SQL_CREATE_ADMINISTRATORS)
        db?.execSQL(UserColumns.SQL_CREATE_USERS)
        db?.execSQL(ProductColumns.SQL_CREATE_PRODUCTS)
        db?.execSQL(CommentColumns.SQL_CREATE_COMMENTS)
        db?.execSQL(BucketColumns.SQL_CREATE_BUCKET)
        db?.execSQL(BucketProductColumns.SQL_CREATE_BUCKET_PRODUCT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(AdministratorColumns.getDropSql())
        db?.execSQL(UserColumns.getDropSql())
        db?.execSQL(ProductColumns.getDropSql())
        db?.execSQL(CommentColumns.getDropSql())
        db?.execSQL(BucketColumns.getDropSql())
        db?.execSQL(BucketProductColumns.getDropSql())
        onCreate(db)
    }
}
