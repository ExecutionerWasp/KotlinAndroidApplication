package com.example.kotlinandroidapplication.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.provider.BaseColumns
import android.support.annotation.RequiresApi
import com.example.kotlinandroidapplication.domain.BucketProduct
import com.example.kotlinandroidapplication.domain.tables.BucketProductColumns

class BucketProductRepository(context: Context): Repository {

    private val bucketRepository = BucketRepository(context)
    private val dbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    override fun closeDb() {
        dbHelper.close()
    }

    fun save(bucketProduct: BucketProduct) {
        if (bucketProduct.id == null) {
            db?.insert(BucketProductColumns.TABLE_NAME, null, bucketProduct.asValues())
        } else {
            db?.update(BucketProductColumns.TABLE_NAME, bucketProduct.asValues(), null, null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun findByBucketId(bucketId: Long): List<BucketProduct> {
        val cursor = db?.query(BucketProductColumns.TABLE_NAME, null, null, null, null, null, null)
        val list = ArrayList<BucketProduct>()
        with(cursor) {
            while (this?.moveToNext()!!) {
                val bucket = BucketProduct(null, null, null)
                bucket.id = getLong(getColumnIndex(BaseColumns._ID))
                bucket.bucketId = getLong(getColumnIndex(BucketProductColumns.COLUMN_NAME_BUCKET_ID))
                bucket.productId = getLong(getColumnIndex(BucketProductColumns.COLUMN_NAME_PRODUCT_ID))
                if (bucket.bucketId == bucketId) {
                    list.add(bucket)
                }
            }
        }
        cursor?.close()

        return list
    }

    fun removeById(bucketId: Long) {
        db?.execSQL("DELETE FROM ${BucketProductColumns.TABLE_NAME} WHERE ${BucketProductColumns.COLUMN_NAME_BUCKET_ID}=$bucketId")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeByProductIdAdnUserId(productId: Long, userId: Long) {
        bucketRepository.openDb()
        val bucket = bucketRepository.findByUserId(userId)
        db?.execSQL("DELETE FROM ${BucketProductColumns.TABLE_NAME} WHERE ${BucketProductColumns.COLUMN_NAME_PRODUCT_ID} = $productId AND ${BucketProductColumns.COLUMN_NAME_BUCKET_ID} = ${bucket.get().id}")
        bucketRepository.closeDb()
    }
}
