package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.example.kotlinandroidapplication.domain.Bucket
import com.example.kotlinandroidapplication.domain.BucketProduct
import com.example.kotlinandroidapplication.repository.AdministratorRepository
import com.example.kotlinandroidapplication.repository.BucketProductRepository
import com.example.kotlinandroidapplication.repository.BucketRepository
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.CustomThread
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger

class ProductDataActivity : Activity() {

    private val administratorRepository = AdministratorRepository(this)
    private val bucketRepository = BucketRepository(this)
    private val bucketProductRepository = BucketProductRepository(this)
    private val logger: Logger = Logger.getLogger(ProductDataActivity::javaClass.name)

    private object MetaData {
        const val ID = "ID: \n"
        const val NAME = "Name: \n"
        const val PRICE = "Price: \n"
        const val DESCRIPTION = "Description: \n"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_data)
        administratorRepository.openDb()
        bucketRepository.openDb()
        bucketProductRepository.openDb()
        val product = HashHelper.product

        UtilsHelper.setTextValue(this, R.id.productId, MetaData.ID + product?.id.toString())
        UtilsHelper.setTextValue(this, R.id.productName, MetaData.NAME + product?.name.toString())
        UtilsHelper.setTextValue(this, R.id.productPrice, MetaData.PRICE + product?.price.toString())
        UtilsHelper.setTextValue(this, R.id.productDescription, MetaData.DESCRIPTION + product?.description.toString())

    }

    fun onComments(v: View) {
        val intent = Intent(this, ProductCommentListActivity::class.java)
        startActivity(intent)
    }

    fun onAddComment(v: View) {
        val intent = Intent(this, ProductCommentAddActivity::class.java)
        startActivity(intent)
    }

    fun onBack(v: View) {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onAddProductToBucket(v: View) {

        val bucketFromDb = HashHelper.user?.id?.let { bucketRepository.findByUserId(it) }
        val product = HashHelper.product

        var bucket = Bucket(null, HashHelper.user?.id, product?.price)
        if (bucketFromDb?.isPresent == true) {
            bucket.id = bucketFromDb.get().id
            bucket.totalPrice = product?.price?.let { bucket.totalPrice?.plus(it) }
        }
        bucketRepository.save(bucket)
        if (bucket.id == null) {
            bucket = HashHelper.user?.id?.let { bucketRepository.findByUserId(it).get() }!!
        }

        val bucketProduct = BucketProduct(null, bucket.id, product?.id)
        bucketProductRepository.save(bucketProduct)

        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }
    //Thread
    override fun onDestroy() {
        super.onDestroy()
        CustomThread().repo(administratorRepository).repo(bucketRepository).repo(bucketProductRepository).start()
    }
}
