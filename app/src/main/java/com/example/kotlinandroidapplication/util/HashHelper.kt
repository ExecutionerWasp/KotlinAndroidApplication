package com.example.kotlinandroidapplication.util

import com.example.kotlinandroidapplication.domain.*

class HashHelper {

    companion object {
        @JvmStatic var product:Product? = null
        @JvmStatic var administrator:Administrator? = null
        @JvmStatic var comment:Comment? = null
        @JvmStatic var user:User? = null
        @JvmStatic var bucket: Bucket? = null
        @JvmStatic var bucketProducts: List<BucketProduct>? = null

        @JvmStatic
        fun init() {
            user = null
            bucket = null
            product = null
            comment = null
            administrator = null
            bucketProducts = null
        }
    }
}
