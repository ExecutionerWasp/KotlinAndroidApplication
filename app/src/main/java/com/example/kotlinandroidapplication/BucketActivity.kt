package com.example.kotlinandroidapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.kotlinandroidapplication.domain.Product
import com.example.kotlinandroidapplication.repository.BucketProductRepository
import com.example.kotlinandroidapplication.repository.BucketRepository
import com.example.kotlinandroidapplication.repository.ProductRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.CustomExecutor
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger
import java.util.stream.Collectors


class BucketActivity : Activity() {

    private val productRepository = ProductRepository(this)
    private val bucketRepository = BucketRepository(this)
    private val bucketProductRepository = BucketProductRepository(this)
    private val logger: Logger = Logger.getLogger(BucketActivity::javaClass.name)


    @SuppressLint("SetTextI18n", "WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productRepository.openDb()
        bucketRepository.openDb()
        bucketProductRepository.openDb()
        setContentView(R.layout.activity_bucket)

        val user = HashHelper.user
        val bucket = user?.id?.let { bucketRepository.findByUserId(it) }
        if (bucket?.isPresent == false) {
            UtilsHelper.showMessage("Bucket is empty.", this)
            return
        }
        HashHelper.bucket = bucket?.get()
        var bucketProducts = bucket?.get()?.id?.
        let { bucketProductRepository.findByBucketId(it) }?.
        stream()?.map { it.productId?.let { it1 ->
            productRepository.findById(it1)
        } }?.map { it.get() }?.collect(Collectors.toList())

        val productsUserEmail = findViewById<TextView>(R.id.productsUserEmailBacket)
        productsUserEmail.text = "User email: ${user?.email.toString()}"
        logger.info("EMAIL: $productsUserEmail")

        if (bucketProducts == null) bucketProducts = ArrayList()

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            bucketProducts.stream().map {
                "Title: ${it.name}\n" +
                        "Price: ${it.price}"
            }.collect(Collectors.toList())
        )

        val listView = findViewById<ListView>(R.id.productListBucket)
        listView.setAdapter(adapter)

        listView.onItemClickListener =
            OnItemClickListener { adapterView, view, position, l ->
                val product = bucketProducts[position]
                HashHelper.product = product
                val intent = Intent(this, ProductRemoveActivity::class.java)
                startActivity(intent)
            }

        val totalPrice = bucketProducts.stream().map { it.price }.mapToDouble{ it.toString().toDouble() }.sum()


        UtilsHelper.setTextValue(this, R.id.totalPrice, "Total price: $totalPrice")
    }

    fun onProductLogOut(v: View) {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }
    //CustomExecutor
    override fun onDestroy() {
        CustomExecutor()
            .repo(productRepository)
            .repo(bucketRepository)
            .repo(bucketProductRepository)
            .execute()
        super.onDestroy()
    }
}
