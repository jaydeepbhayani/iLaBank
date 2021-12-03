package com.jaydeepbhayani.ilabank.data.repository

import android.content.Context
import com.jaydeepbhayani.ilabank.data.api.remote.RemoteDataSourceImpl

/**
 * *[Injection]
 *
 * All the viewModel Injections will go here.
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */
object Injection {
    fun provideDataRepository(context: Context) =
        DataRepository(
            RemoteDataSourceImpl.newInstance(context)
        )
}