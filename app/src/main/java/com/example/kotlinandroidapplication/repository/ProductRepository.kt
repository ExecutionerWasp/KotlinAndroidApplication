package com.example.kotlinandroidapplication.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.provider.BaseColumns
import android.support.annotation.RequiresApi
import com.example.kotlinandroidapplication.domain.Product
import com.example.kotlinandroidapplication.domain.tables.ProductColumns
import com.example.kotlinandroidapplication.util.HashHelper
import java.util.*

class ProductRepository(context: Context) : Repository {

    private val commentRepository = CommentRepository(context)
    private val bucketProductRepository = BucketProductRepository(context)
    private val dbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    override fun closeDb() {
        dbHelper.close()
    }

    fun save(product: Product) {
        if (product.id == null) {
            db?.insert(ProductColumns.TABLE_NAME, null, product.asValues())
        } else {
            db?.update(ProductColumns.TABLE_NAME, product.asValues(), null, null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findAll(): List<Product> {
        val cursor = db?.query(ProductColumns.TABLE_NAME, null, null, null, null, null, null)

        val products = ArrayList<Product>()

        with(cursor) {
            while (this?.moveToNext()!!) {
                val product = Product()
                product.id = getLong(getColumnIndex(BaseColumns._ID))
                product.name = getString(getColumnIndex(ProductColumns.COLUMN_NAME_NAME))
                product.price = getDouble(getColumnIndex(ProductColumns.COLUMN_NAME_PRICE))
                product.description =
                    getString(getColumnIndex(ProductColumns.COLUMN_NAME_DESCRIPTION))
                product.imgUrl = getString(getColumnIndex(ProductColumns.COLUMN_NAME_IMG_URL))
                product.authorId = getLong(getColumnIndex(ProductColumns.COLUMN_NAME_AUTHOR_ID))
                products.add(product)
            }
        }
        cursor?.close()
        products.sortBy { it.id }
        return products
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findById(id: Long): Optional<Product> {
        val findAll = findAll()
        for (local in findAll) {
            if (local.id == id) {
                return Optional.of(local)
            }
        }
        return Optional.empty()
    }

    fun removeById(productId: Long) {
        if (HashHelper.administrator != null) {
            db?.execSQL("DELETE FROM ${ProductColumns.TABLE_NAME} WHERE _id=$productId")
            commentRepository.openDb()
            commentRepository.findByProductId(productId)
            commentRepository.closeDb()
        } else {
            bucketProductRepository.openDb()
            HashHelper.user?.id?.let {
                bucketProductRepository.removeByProductIdAdnUserId(productId,
                    it
                )
            }
            bucketProductRepository.closeDb()
        }

    }
}
