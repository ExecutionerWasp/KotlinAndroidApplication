package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.example.kotlinandroidapplication.domain.Comment
import com.example.kotlinandroidapplication.repository.AdministratorRepository
import com.example.kotlinandroidapplication.repository.CommentRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger

class ProductCommentAddActivity : Activity() {

    private val commentRepository = CommentRepository(this)
    private val logger: Logger = Logger.getLogger(ProductCommentAddActivity::javaClass.name)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add_comment)
        commentRepository.openDb()
    }

    fun onBack(v: View) {
        val intent = Intent(this, ProductCommentListActivity::class.java)
        startActivity(intent)
    }

    fun onAddComment(v: View) {
        val comment = Comment(null, null, null, null)
        if (HashHelper.comment != null) {
            comment.id = HashHelper.comment?.id
        }
        comment.productId = HashHelper.product?.id
        comment.userId = HashHelper.user?.id
        comment.text = UtilsHelper.getTextValue(this, R.id.commentAdd)?.text.toString()
        commentRepository.save(comment)
        UtilsHelper.showMessage("Comment has been saved.", this)
        val intent = Intent(this, ProductCommentListActivity::class.java)
        startActivity(intent)
        HashHelper.comment = null
    }
    //AsyncTask
    override fun onDestroy() {
        super.onDestroy()
        AsyncTaskImplementation().execute(commentRepository)
    }
}
