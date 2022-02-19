package com.example.kotlinandroidapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.TextView
import com.example.kotlinandroidapplication.repository.UserRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.CustomExecutor
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.time.LocalDateTime
import java.util.logging.Logger

class MainActivity : Activity() {

    val userRepository = UserRepository(this)
    private val logger: Logger = Logger.getLogger(MainActivity::javaClass.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRepository.openDb()
        setContentView(R.layout.activity_main)
        HashHelper.init()
    }

    fun onClick(v: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun onLogin(v: View) {
        val login = findViewById<TextView>(R.id.adminLogin)
        val password = findViewById<TextView>(R.id.adminPassword)

        val user = userRepository.findByLoginAndPassword(login.text.toString(), password.text.toString())

        if (user.id == null) {
            UtilsHelper.showMessage("User not registered.", this)
        } else {
            user.lastAuthorized = LocalDateTime.now()
            userRepository.save(user)
            logger.info("User: $user")
            logger.info("Size: ${userRepository.findAll().size}")
            HashHelper.user = user
            val intent = Intent(this, ProductsActivity::class.java)
            startActivity(intent)
        }

    }

    fun onLoginAsAdmin(v: View) {
        val intent = Intent(this, AdministrationLoginActivity::class.java)
        startActivity(intent)
    }
    //CustomExecutor
    override fun onDestroy() {
        CustomExecutor().repo(userRepository).execute()
        super.onDestroy()
    }
}
