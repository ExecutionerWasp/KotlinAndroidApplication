package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.kotlinandroidapplication.repository.CommentRepository
import com.example.kotlinandroidapplication.repository.ProductRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.CustomExecutor
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger

class ProductRemoveActivity : Activity() {

    private val productRepository = ProductRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productRepository.openDb()
        setContentView(R.layout.activity_product_remove)
    }

    fun onRemove(v: View) {
        if(HashHelper.administrator != null) {
            val intent = Intent(this, AdministrationActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, BucketActivity::class.java)
            startActivity(intent)
        }
        HashHelper.product?.id?.let { productRepository.removeById(it) }
        UtilsHelper.showMessage("Product '${HashHelper.product?.name}' removed.",this)
    }

    fun onBack(v: View) {
        val intent = Intent(this, AdministrationActivity::class.java)
        startActivity(intent)
    }
    //CustomExecutor
    override fun onDestroy() {
        CustomExecutor().repo(productRepository).execute()
        super.onDestroy()
    }
}
