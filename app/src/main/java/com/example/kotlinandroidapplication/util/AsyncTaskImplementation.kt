package com.example.kotlinandroidapplication.util

import android.os.AsyncTask
import com.example.kotlinandroidapplication.repository.Repository

class AsyncTaskImplementation: AsyncTask<Repository, Integer, Long>() {

    public override fun doInBackground(vararg repo: Repository?): Long {
        repo.forEach { it?.closeDb() }
        return 1
    }
}
