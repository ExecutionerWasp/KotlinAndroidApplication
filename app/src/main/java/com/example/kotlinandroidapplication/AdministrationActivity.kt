package com.example.kotlinandroidapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.example.kotlinandroidapplication.repository.AdministratorRepository
import com.example.kotlinandroidapplication.util.AsyncTaskImplementation
import com.example.kotlinandroidapplication.util.HashHelper
import java.util.logging.Logger

class AdministrationActivity : Activity() {

    private val administratorRepository = AdministratorRepository(this)
    private val logger: Logger = Logger.getLogger(AdministrationActivity::javaClass.name)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrator)
        administratorRepository.openDb()
    }

    fun onBack(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun onAddProduct(v: View) {
        val intent = Intent(this, AdministrationProductAddActivity::class.java)
        startActivity(intent)
    }

    fun onAdministratorRegistration(v: View) {
        val intent = Intent(this, AdministrationRegistrationActivity::class.java)
        startActivity(intent)
    }

    //AsyncTask
    override fun onDestroy() {
        super.onDestroy()
        HashHelper.administrator = null
        AsyncTaskImplementation().execute(administratorRepository)
    }
}
