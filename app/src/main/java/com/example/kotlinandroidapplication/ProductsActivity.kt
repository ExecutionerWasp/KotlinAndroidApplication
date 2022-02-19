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
import com.example.kotlinandroidapplication.repository.AdministratorRepository
import com.example.kotlinandroidapplication.repository.BucketProductRepository
import com.example.kotlinandroidapplication.repository.ProductRepository
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.CustomThread
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger
import java.util.stream.Collectors


class ProductsActivity : Activity() {

    private val productRepository = ProductRepository(this)
    private val bucketProductRepository = BucketProductRepository(this)
    private val administratorRepository = AdministratorRepository(this)
    private val logger: Logger = Logger.getLogger(ProductsActivity::javaClass.name)


    @SuppressLint("SetTextI18n", "WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productRepository.openDb()
        administratorRepository.openDb()
        bucketProductRepository.openDb()
        setContentView(R.layout.activity_products)
        val user = HashHelper.user
        val productsUserEmail = findViewById<TextView>(R.id.productsUserEmail)
        productsUserEmail.text = "User email: ${user?.email.toString()}"
        logger.info("EMAIL: $productsUserEmail")

        val products = productRepository.findAll()

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            products.stream().map {
                "Title: ${it.name}\n" +
                        "Price: ${it.price}\n" +
                        "Author: ${administratorRepository.findById(it.authorId).login}"
            }.collect(Collectors.toList())
        )

        val listView = findViewById<ListView>(R.id.productList)
        listView.setAdapter(adapter)

        listView.onItemClickListener =
            OnItemClickListener { adapterView, view, position, l ->
                val product = products[position]
                HashHelper.product = product
                val intent = Intent(this, ProductDataActivity::class.java)
                startActivity(intent)
            }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSearch(v: View) {
        val id = UtilsHelper.getTextValue(this, R.id.search)?.text.toString().toLong()
        val product = productRepository.findById(id)
        if (product.isPresent) {
            HashHelper.product = product.get()
            val intent = Intent(this, ProductDataActivity::class.java)
            startActivity(intent)
        } else {
            UtilsHelper.showMessage("Product with id: $id not found.", this)
        }

    }

    fun onBucket(v: View) {
        val intent = Intent(this, BucketActivity::class.java)
        startActivity(intent)
    }

    fun onBack(v: View) {
        if (HashHelper.bucket != null) {
            HashHelper.bucket?.id?.let { bucketProductRepository.removeById(it) }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    //Thread
    override fun onDestroy() {
        CustomThread().repo(productRepository).repo(administratorRepository).repo(bucketProductRepository).start()
        super.onDestroy()
    }
}
