package com.jaydeepbhayani.ilabank.data.api


/**
 * * [Exception]
 * RemoteDataNotFoundException to handle exception from response
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */

open class DataSourceException(message: String? = null) : Exception(message)

class RemoteDataNotFoundException : DataSourceException("Data not Found")