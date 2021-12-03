package com.jaydeepbhayani.ilabank.data.api.remote

import android.content.Context
import android.util.Log
import com.jaydeepbhayani.ilabank.data.api.ApiService
import com.jaydeepbhayani.ilabank.data.api.RemoteDataNotFoundException
import com.jaydeepbhayani.ilabank.data.model.TopHeadLinesResponse
import com.jaydeepbhayani.ilabank.data.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 *  *  [RemoteDataSourceImpl]
 * implementation for RemoteDataSource
 *  @author
 *  created by Jaydeep Bhayani on 03/12/2021
 */

class RemoteDataSourceImpl private constructor(
    private val apiService: ApiService
    ) : RemoteDataSource {

    /**
     *  get top headlines according to given country
     */
    override suspend fun getTopHeadlinesData(
        country: String,
        pageSize: Int,
        page: Int
    ): Result<TopHeadLinesResponse> =
        withContext(Dispatchers.IO) {
            /*val request =
                apiService.getTopHeadlinesDataAsync(
                    TOP_HEADLINES_URL
                        .replace("{country}", country)
                )*/

            val request =
                apiService.getTopHeadlinesDataAsync(
                    country, pageSize, page
                )


            try {
                val response  = request.await()
                //if (response.status.equals("ok", ignoreCase = true))
                    Log.d("test", "result.getTopHeadlinesData : $response")
                    Result.Success(response)

            } catch (ex: HttpException) {
                Result.Error(RemoteDataNotFoundException())
            } catch (ex: Throwable) {
                Result.Error(RemoteDataNotFoundException())
            }
        }


    /**
     *  get top headlines according to given country
     */
    override suspend fun getTopHeadlinesData(
        query: String
    ): Result<TopHeadLinesResponse> =
        withContext(Dispatchers.IO) {
            /*val request =
                apiService.getTopHeadlinesDataAsync(
                    TOP_HEADLINES_URL
                        .replace("{country}", country)
                )*/

            val request = apiService.getTopHeadlinesDataAsync(query)

            try {
                val response  = request.await()
                //if (response.status.equals("ok", ignoreCase = true))
                Log.d("test", "result.getTopHeadlinesData : $response")
                Result.Success(response)

            } catch (ex: HttpException) {
                Result.Error(RemoteDataNotFoundException())
            } catch (ex: Throwable) {
                Result.Error(RemoteDataNotFoundException())
            }
        }

    companion object {
        fun newInstance(context: Context) =
            RemoteDataSourceImpl(
                ApiService.create(context)
            )
    }
}