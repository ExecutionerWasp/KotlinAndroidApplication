package com.example.kotlinandroidapplication.util

import android.app.Activity
import android.content.Context
import android.widget.TextView
import android.widget.Toast

class UtilsHelper {

    companion object{
        @JvmStatic
        fun showMessage(message:String, context: Context) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        @JvmStatic
        fun getTextValue(activity: Activity, id: Int): TextView? {
            return activity.findViewById(id)
        }

        @JvmStatic
        fun setTextValue(activity: Activity, id: Int, message:String) {
            val editText = getTextValue(activity, id)
            editText?.text = message
        }
    }
}
