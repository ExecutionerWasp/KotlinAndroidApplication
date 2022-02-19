package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.kotlinandroidapplication.repository.AdministratorRepository
import com.example.kotlinandroidapplication.repository.ProductRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.HashHelper
import java.util.logging.Logger
import java.util.stream.Collectors

class AdministrationActivity : Activity() {

    private val administratorRepository = AdministratorRepository(this)
    private val productRepository = ProductRepository(this)
    private val logger: Logger = Logger.getLogger(AdministrationActivity::javaClass.name)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrator)
        administratorRepository.openDb()
        productRepository.openDb()

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

        val listView = findViewById<ListView>(R.id.productListAdmin)
        listView.setAdapter(adapter)

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                val product = products[position]
                HashHelper.product = product
                val intent = Intent(this, ProductRemoveActivity::class.java)
                startActivity(intent)
            }
    }

    fun onBack(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        HashHelper.init()
    }

    fun onAddProduct(v: View) {
        val intent = Intent(this, AdministrationProductAddActivity::class.java)
        startActivity(intent)
    }

    fun onAdministratorRegistration(v: View) {
        val intent = Intent(this, AdministrationRegistrationActivity::class.java)
        startActivity(intent)
    }

    //AsyncTask
    override fun onDestroy() {
        super.onDestroy()
        HashHelper.administrator = null
        AsyncTaskImplementation().execute(administratorRepository)
        AsyncTaskImplementation().execute(productRepository)
    }
}
