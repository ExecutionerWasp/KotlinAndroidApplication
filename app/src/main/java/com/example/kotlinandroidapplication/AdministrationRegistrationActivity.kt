package com.example.kotlinandroidapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.TextView
import com.example.kotlinandroidapplication.domain.Administrator
import com.example.kotlinandroidapplication.repository.AdministratorRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.time.LocalDateTime
import java.util.logging.Logger

class AdministrationRegistrationActivity : Activity() {

    private val administratorRepository = AdministratorRepository(this)
    private val logger: Logger = Logger.getLogger(AdministrationRegistrationActivity::javaClass.name)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrator_registration)
        administratorRepository.openDb()
    }

    fun onBack(v: View) {
        val intent = Intent(this, AdministrationActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun onAdministratorRegistration(v: View) {
        val login = findViewById<TextView>(R.id.registrationLoginAdmin)
        val password = findViewById<TextView>(R.id.registrationPasswordAdmin)

        val exists = administratorRepository.existsByLogin(login.text.toString())
        if (exists) {
            UtilsHelper.showMessage("Administrator not registered.", this)
        } else {
            val administrator = Administrator(null, login.text.toString(), password.text.toString(), LocalDateTime.now())
            administratorRepository.save(administrator)
            val intent = Intent(this, AdministrationActivity::class.java)
            startActivity(intent)
        }

    }
    //AsyncTask
    override fun onDestroy() {
        super.onDestroy()
        AsyncTaskImplementation().execute(administratorRepository)
    }
}
