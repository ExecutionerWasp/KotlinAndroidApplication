package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.TextView
import com.example.kotlinandroidapplication.domain.Product
import com.example.kotlinandroidapplication.repository.AdministratorRepository
import com.example.kotlinandroidapplication.repository.ProductRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger

class AdministrationProductAddActivity : Activity() {

    private val administratorRepository = AdministratorRepository(this)
    private val productRepository = ProductRepository(this)
    private val logger: Logger = Logger.getLogger(AdministrationProductAddActivity::javaClass.name)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)
        administratorRepository.openDb()
        productRepository.openDb()
    }

    fun onBack(v: View) {
        val intent = Intent(this, AdministrationActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAddNewProduct(v: View) {

        val name = findViewById<TextView>(R.id.productAddName)
        val price = findViewById<TextView>(R.id.productAddPrice)
        val description = findViewById<TextView>(R.id.productAddDescription)

        val product = Product(null, name.text.toString(),
            if(price.text.toString() == "")  "0".toDouble() else price.text.toString().toDouble(),
            description.text.toString(), "", HashHelper.administrator?.id)

        productRepository.save(product)

        logger.info("PRODUCTS: ${productRepository.findAll()}")
        val intent = Intent(this, AdministrationActivity::class.java)
        startActivity(intent)
        UtilsHelper.showMessage("Product with name: ${name.text} has been saved.", this)
    }
    //AsyncTask
    override fun onDestroy() {
        super.onDestroy()
        AsyncTaskImplementation().execute(administratorRepository, productRepository)
    }
}
