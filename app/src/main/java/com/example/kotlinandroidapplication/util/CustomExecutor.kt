package com.example.kotlinandroidapplication.util

import com.example.kotlinandroidapplication.repository.Repository
import java.util.concurrent.Executor

class CustomExecutor: Executor {

    var repository = ArrayList<Repository>()

    fun repo(repo: Repository): CustomExecutor {
        repository.add(repo)
        return this
    }

    fun execute(){
        repository.forEach { this.execute { it.closeDb() } }
    }

    override fun execute(runnable: Runnable?) {
        runnable?.run()
    }
}
