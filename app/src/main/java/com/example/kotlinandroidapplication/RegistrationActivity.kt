package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.TextView
import com.example.kotlinandroidapplication.domain.User
import com.example.kotlinandroidapplication.repository.UserRepository
import com.example.kotlinandroidapplication.util.CustomThread
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.time.LocalDateTime
import java.util.logging.Logger

class RegistrationActivity : Activity() {

    private val logger: Logger = Logger.getLogger(RegistrationActivity::javaClass.name)
    private val userRepository = UserRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun onClick(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onApply(v: View) {
        logger.info("Registered")

        val login = findViewById<TextView>(R.id.registrationLogin)
        val password = findViewById<TextView>(R.id.registrationPassword)
        val email = findViewById<TextView>(R.id.registrationEmail)

        userRepository.openDb()
        if (userRepository.existsByLogin(login.text.toString())) {
            UtilsHelper.showMessage("User with this login is already exists.", this)
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        } else {
            val user = User(null, login.text.toString(), password.text.toString(), email.text.toString(), LocalDateTime.now())
            userRepository.save(user)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //Thread
    override fun onDestroy() {
        super.onDestroy()
        CustomThread().repo(userRepository).start()
    }
}
