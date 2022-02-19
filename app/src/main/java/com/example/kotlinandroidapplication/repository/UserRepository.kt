package com.example.kotlinandroidapplication.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.provider.BaseColumns
import android.support.annotation.RequiresApi
import com.example.kotlinandroidapplication.domain.User
import com.example.kotlinandroidapplication.domain.tables.CommentColumns
import com.example.kotlinandroidapplication.domain.tables.UserColumns
import java.time.LocalDateTime
import java.util.stream.Collectors

class UserRepository(context: Context) : Repository{

    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    override fun closeDb() {
        dbHelper.close()
    }

    fun existsByLogin(login: String): Boolean {
        val cursor = db?.query(UserColumns.TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (this?.moveToNext()!!) {
                val log = getString(getColumnIndex(UserColumns.COLUMN_NAME_LOGIN))
                if (login == log) {
                    return true
                }
            }
        }

        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save(user: User) {
        if (user.id == null) {
            if (findAll().isEmpty() == false) {
                val list = findAll().stream().mapToLong { it.id.toString().toLong() }.boxed().sorted().collect(Collectors.toList())
                val lastId = list.get(list.size - 1)
                user.id = lastId.plus(1)
            }
            db?.execSQL("INSERT INTO ${UserColumns.TABLE_NAME} (${UserColumns.COLUMN_NAME_LOGIN}, ${UserColumns.COLUMN_NAME_PASSWORD}, ${UserColumns.COLUMN_NAME_EMAIL}) VALUES (\"${user.login}\", \"${user.password}\", \"${user.email}\")")
//            db?.insert(UserColumns.TABLE_NAME, null, user.asValues())
        } else {
            db?.execSQL("UPDATE ${UserColumns.TABLE_NAME} SET ${BaseColumns._ID} = ${user.id}, ${UserColumns.COLUMN_NAME_LOGIN} = '${user.login}', ${UserColumns.COLUMN_NAME_PASSWORD}= '${user.password}', ${UserColumns.COLUMN_NAME_EMAIL}='${user.email}' WHERE ${BaseColumns._ID} = ${user.id};")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findByLoginAndPassword(login: String, password: String): User {
        val user = User()
        val cursor = db?.query(UserColumns.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (this?.moveToNext()!!) {
                val log = getString(getColumnIndex(UserColumns.COLUMN_NAME_LOGIN))
                val pass = getString(getColumnIndex(UserColumns.COLUMN_NAME_PASSWORD))
                if (login == log && password == pass) {
                    user.login = log
                    user.password = pass
                    user.email = getString(getColumnIndex(UserColumns.COLUMN_NAME_EMAIL))
                    user.id = getLong(getColumnIndex(BaseColumns._ID))
                    break
                }
            }
        }
        cursor?.close()
        return user
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findById(id:Long): User {
        val user = User()
        val cursor = db?.query(UserColumns.TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (this?.moveToNext()!!) {
                val localId = getLong(getColumnIndex(BaseColumns._ID))
                if (id == localId) {
                    user.id = getLong(getColumnIndex(BaseColumns._ID))
                    user.login = getString(getColumnIndex(UserColumns.COLUMN_NAME_LOGIN))
                    user.email = getString(getColumnIndex(UserColumns.COLUMN_NAME_EMAIL))
                    user.password = getString(getColumnIndex(UserColumns.COLUMN_NAME_PASSWORD))
                    break
                }
            }
        }
        cursor?.close()
        return user
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findLastAuthorizedUser(): User {

        val cursor = db?.query(UserColumns.TABLE_NAME, null, null, null, null, null, null)

        val users = ArrayList<User>()

        with(cursor) {
            while (this?.moveToNext()!!) {
                val user = User()
                user.login = getString(getColumnIndex(UserColumns.COLUMN_NAME_LOGIN))
                user.password = getString(getColumnIndex(UserColumns.COLUMN_NAME_PASSWORD))
                user.email = getString(getColumnIndex(UserColumns.COLUMN_NAME_EMAIL))
                user.id = getLong(getColumnIndex(BaseColumns._ID))
                users.add(user)
            }
        }
        cursor?.close()
        users.sortBy { it.lastAuthorized }
        return users[users.size - 1]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findAll(): ArrayList<User> {
        val cursor = db?.query(UserColumns.TABLE_NAME, null, null, null, null, null, null)

        val users = ArrayList<User>()

        with(cursor) {
            while (this?.moveToNext()!!) {
                val user = User()
                user.id = getLong(getColumnIndex(BaseColumns._ID))
                user.login = getString(getColumnIndex(UserColumns.COLUMN_NAME_LOGIN))
                user.email = getString(getColumnIndex(UserColumns.COLUMN_NAME_EMAIL))
                user.password = getString(getColumnIndex(UserColumns.COLUMN_NAME_PASSWORD))
                users.add(user)
            }
        }
        cursor?.close()
        return users
    }
}
