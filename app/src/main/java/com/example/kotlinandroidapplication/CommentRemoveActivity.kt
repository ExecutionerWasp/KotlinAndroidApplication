package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.kotlinandroidapplication.repository.CommentRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.CustomExecutor
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.util.logging.Logger

class CommentRemoveActivity : Activity() {

    val commentRepository = CommentRepository(this)
    private val logger: Logger = Logger.getLogger(CommentRemoveActivity::javaClass.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commentRepository.openDb()
        setContentView(R.layout.activity_comment_remove)
    }

    fun onUpdate(v: View) {
        val intent = Intent(this, ProductCommentAddActivity::class.java)
        startActivity(intent)
    }

    fun onRemove(v: View) {
        val intent = Intent(this, ProductCommentListActivity::class.java)
        startActivity(intent)
        HashHelper.comment?.id?.let { commentRepository.removeById(it) }
        UtilsHelper.showMessage("Comment removed.",this)
    }

    fun onBack(v: View) {
        val intent = Intent(this, ProductCommentListActivity::class.java)
        startActivity(intent)
    }
    //CustomExecutor
    override fun onDestroy() {
        CustomExecutor().repo(commentRepository).execute()
        super.onDestroy()
    }
}
