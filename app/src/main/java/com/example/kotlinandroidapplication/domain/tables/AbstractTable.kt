package com.example.kotlinandroidapplication.domain.tables

interface AbstractTable {

    fun getTableName(): String

    fun getDropSql(): String {
       return "DROP TABLE IF EXISTS ${getTableName()}"
    }
}
