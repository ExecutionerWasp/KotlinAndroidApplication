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
import com.example.kotlinandroidapplication.util.HashHelper
import com.example.kotlinandroidapplication.util.UtilsHelper
import java.time.LocalDateTime
import java.util.logging.Logger

class AdministrationLoginActivity : Activity() {

    private val administratorRepository = AdministratorRepository(this)
    private val logger: Logger = Logger.getLogger(AdministrationLoginActivity::javaClass.name)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrator_login)
        administratorRepository.openDb()
    }

    fun onBack(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun onAdministratorLogin(v: View) {
        val admin = Administrator(null, "admin", "admin", LocalDateTime.now())
        if (!administratorRepository.existsByLogin(admin.login)) {
            administratorRepository.save(admin)
        }


        val login = findViewById<TextView>(R.id.adminLogin)
        val password = findViewById<TextView>(R.id.adminPassword)

        val administrator = administratorRepository.findByLoginAndPassword(login.text.toString(), password.text.toString())

        if (administrator.id == null) {
            UtilsHelper.showMessage("Administrator does not exists.", this)
        } else {
            administratorRepository.save(administrator)
            logger.info("Administrator: $administrator")
            logger.info("Size: ${administratorRepository.findAll().size}")

            HashHelper.administrator = administrator
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
