package com.jaydeepbhayani.ilabank.data.repository

/**
 * *[Result]
 *
 * Helper class for api data used in retrofit
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */
sealed class Result<out T : Any> {

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(val exception: Throwable) : Result<Nothing>()
}