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
import com.example.kotlinandroidapplication.repository.CommentRepository
import com.example.kotlinandroidapplication.repository.ProductRepository
import com.example.kotlinandroidapplication.repository.UserRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger
import java.util.stream.Collectors

class ProductCommentListActivity : Activity() {

    private val commentRepository = CommentRepository(this)
    private val userRepository = UserRepository(this)
    private val productRepository = ProductRepository(this)
    private val logger: Logger = Logger.getLogger(ProductCommentListActivity::javaClass.name)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_comment_list)
        commentRepository.openDb()
        productRepository.openDb()
        userRepository.openDb()

        val comments = commentRepository.findByProductId(HashHelper.product?.id)
        UtilsHelper.setTextValue(this, R.id.productComment, "Product: ${HashHelper.product?.id?.let {
            productRepository.findById(
                it
            ).get().name
        }}")
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            comments.stream().map {
                "User: ${it.userId?.let { it1 -> userRepository.findById(it1).email }}\n" +
                        "Comment: ${it.text}"
            }.collect(Collectors.toList())
        )
        val listView = findViewById<ListView>(R.id.productComments)
        listView.setAdapter(adapter)
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                val comment = comments[position]
                HashHelper.comment = comment
                val intent = Intent(this, CommentRemoveActivity::class.java)
                startActivity(intent)
            }
    }

    fun onBack(v: View) {
        val intent = Intent(this, ProductDataActivity::class.java)
        startActivity(intent)
    }

    fun onAddComment(v: View) {
        val intent = Intent(this, ProductCommentAddActivity::class.java)
        startActivity(intent)
    }
    //AsyncTask
    override fun onDestroy() {
        super.onDestroy()
        AsyncTaskImplementation().execute(commentRepository, userRepository)
    }
}
