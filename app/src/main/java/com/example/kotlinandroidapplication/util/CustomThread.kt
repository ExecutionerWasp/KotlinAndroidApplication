package com.example.kotlinandroidapplication.util

import com.example.kotlinandroidapplication.repository.Repository

class CustomThread: Thread() {

    var repository = ArrayList<Repository>()

    fun repo(repo: Repository): CustomThread {
        repository.add(repo)
        return this
    }

    override fun run() {
        println("${currentThread()} has run.")
        repository.forEach{ it.closeDb()}
    }
}
