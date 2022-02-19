package com.example.kotlinandroidapplication.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.kotlinandroidapplication.domain.Comment
import com.example.kotlinandroidapplication.domain.tables.CommentColumns
import com.example.kotlinandroidapplication.domain.tables.UserColumns

class CommentRepository(context: Context): Repository {

    private val dbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    override fun closeDb() {
        dbHelper.close()
    }

    fun save(comment: Comment) {
        if (comment.id == null) {
            db?.insert(CommentColumns.TABLE_NAME, null, comment.asValues())
        } else {
            db?.execSQL("UPDATE ${CommentColumns.TABLE_NAME} SET ${BaseColumns._ID} = ${comment.id}, ${CommentColumns.COLUMN_NAME_USER_ID} = ${comment.userId}, ${CommentColumns.COLUMN_NAME_PRODUCT_ID}= ${comment.productId}, ${CommentColumns.COLUMN_NAME_TEXT}='${comment.text}' WHERE ${BaseColumns._ID} = ${comment.id};")
        }
    }

    fun findByProductIdAndUserId(userId: Long?, productId: Long?): List<Comment> {
        var comment = Comment(null, null, null, null)
        val cursor = db?.query(UserColumns.TABLE_NAME, null, null, null, null, null, null)
        val comments = ArrayList<Comment>()
        with(cursor) {
            while (this?.moveToNext()!!) {
                comment.id = getLong(getColumnIndex(BaseColumns._ID))
                comment.text = getString(getColumnIndex(CommentColumns.COLUMN_NAME_TEXT))
                comment.userId = getLong(getColumnIndex(CommentColumns.COLUMN_NAME_USER_ID))
                comment.productId = getLong(getColumnIndex(CommentColumns.COLUMN_NAME_PRODUCT_ID))
                if (comment.productId == productId && comment.userId == userId) {
                    comments.add(comment)
                }
                comment = Comment(null, null, null, null)
            }
        }
        cursor?.close()
        return comments
    }

    fun findByProductId(productId: Long?): List<Comment> {
        var comment = Comment(null, null, null, null)
        val cursor = db?.query(CommentColumns.TABLE_NAME, null, null, null, null, null, null)
        val comments = ArrayList<Comment>()
        with(cursor) {
            while (this?.moveToNext()!!) {
                comment.id = getLong(getColumnIndex(BaseColumns._ID))
                comment.text = getString(getColumnIndex(CommentColumns.COLUMN_NAME_TEXT))
                comment.userId = getLong(getColumnIndex(CommentColumns.COLUMN_NAME_USER_ID))
                comment.productId = getLong(getColumnIndex(CommentColumns.COLUMN_NAME_PRODUCT_ID))
                if (comment.productId == productId) {
                    comments.add(comment)
                }
                comment = Comment(null, null, null, null)
            }
        }
        cursor?.close()
        return comments
    }

    fun removeById(commentId: Long) {
        db?.execSQL("DELETE FROM ${CommentColumns.TABLE_NAME} WHERE _id=$commentId")
    }
}
