package com.example.kotlinandroidapplication.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.provider.BaseColumns
import android.support.annotation.RequiresApi
import com.example.kotlinandroidapplication.domain.Administrator
import com.example.kotlinandroidapplication.domain.tables.AdministratorColumns

class AdministratorRepository(context: Context) : Repository {

    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    override fun closeDb() {
        dbHelper.close()
    }

    fun existsByLogin(login: String): Boolean {
        val cursor = db?.query(AdministratorColumns.TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (this?.moveToNext()!!) {
                val log = getString(getColumnIndex(AdministratorColumns.COLUMN_NAME_LOGIN))
                if (login == log) {
                    return true
                }
            }
        }

        return false
    }

    fun save(administrator: Administrator) {
        db?.execSQL("INSERT INTO ${AdministratorColumns.TABLE_NAME} (${AdministratorColumns.COLUMN_NAME_LOGIN}, ${AdministratorColumns.COLUMN_NAME_PASSWORD}) VALUES (\"${administrator.login}\", \"${administrator.password}\")")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findByLoginAndPassword(login: String, password: String): Administrator {
        val administrator = Administrator()
        val cursor = db?.query(AdministratorColumns.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (this?.moveToNext()!!) {
                val log = getString(getColumnIndex(AdministratorColumns.COLUMN_NAME_LOGIN))
                val pass = getString(getColumnIndex(AdministratorColumns.COLUMN_NAME_PASSWORD))
                if (login == log && password == pass) {
                    administrator.id = getLong(getColumnIndex(BaseColumns._ID))
                    administrator.login = log
                    administrator.password = pass
                    break
                }
            }
        }
        cursor?.close()
        return administrator
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findAll(): ArrayList<Administrator> {
        val cursor = db?.query(AdministratorColumns.TABLE_NAME, null, null, null, null, null, null)

        val administrators = ArrayList<Administrator>()

        with(cursor) {
            while (this?.moveToNext()!!) {
                val administrator = Administrator()
                administrator.login =
                    getString(getColumnIndex(AdministratorColumns.COLUMN_NAME_LOGIN))
                administrator.password =
                    getString(getColumnIndex(AdministratorColumns.COLUMN_NAME_PASSWORD))
                administrator.id = getLong(getColumnIndex(BaseColumns._ID))
                administrators.add(administrator)
            }
        }
        cursor?.close()
        return administrators
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findById(id: Long?): Administrator {
        val admin = Administrator()
        for (administrator in findAll()) {
            if (administrator.id == id) {
                return administrator
            }
        }
        return admin
    }

}
