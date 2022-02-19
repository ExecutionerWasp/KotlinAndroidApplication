package com.example.kotlinandroidapplication.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.provider.BaseColumns
import android.support.annotation.RequiresApi
import com.example.kotlinandroidapplication.domain.Bucket
import com.example.kotlinandroidapplication.domain.tables.BucketColumns
import com.example.kotlinandroidapplication.domain.tables.BucketProductColumns
import com.example.kotlinandroidapplication.domain.tables.ProductColumns
import java.util.*

class BucketRepository(context: Context) : Repository{

    private val dbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    override fun closeDb() {
        dbHelper.close()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun save(bucket: Bucket) {
        when {
            bucket.id == null -> {
                db?.insert(BucketColumns.TABLE_NAME, null, bucket.asValues())
            }
            bucket.userId?.let { findByUserId(it).isPresent } == true -> {
                db?.update(BucketColumns.TABLE_NAME, bucket.asValues(), null, null)
            }
            else -> {
                db?.update(BucketColumns.TABLE_NAME, bucket.asValues(), null, null)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun findByUserId(userId: Long): Optional<Bucket> {
        val cursor = db?.query(BucketColumns.TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (this?.moveToNext()!!) {
                val bucket = Bucket(null, null, null)
                bucket.id = getLong(getColumnIndex(BaseColumns._ID))
                bucket.userId = getLong(getColumnIndex(BucketColumns.COLUMN_NAME_USER_ID))
                bucket.totalPrice = getDouble(getColumnIndex(BucketColumns.COLUMN_NAME_TOTAL_PRICE))
                if (bucket.userId == userId) {
                    return Optional.of(bucket)
                }
            }
        }
        cursor?.close()

        return Optional.empty()
    }
}
