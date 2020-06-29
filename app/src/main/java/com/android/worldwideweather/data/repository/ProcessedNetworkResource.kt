package com.android.worldwideweather.data.repository

import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

abstract class ProcessedNetworkResource<RequestType, ResultType : Any> {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)

        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        GlobalScope.launch {
            try {
                val apiResponse = createCall()

                when {
                    apiResponse.isSuccessful -> {
                        setValue(Resource.success(apiResponse.body()?.let { processResponse(it) }))
                    }
                    apiResponse.errorBody() != null -> {
                        val error = getErrorMessage(apiResponse.errorBody())
                        setValue(Resource.error(error.message, error.code, null))
                    }
                    else -> {
                        setValue(
                            Resource.error(
                                apiResponse.message(),
                                apiResponse.code().toString(),
                                null
                            )
                        )
                    }
                }
            } catch (e: HttpException) {
                setValue(Resource.error(e.message.orEmpty(), e.code().toString(), null))
            } catch (e: Exception) {
                setValue(Resource.error(e.message.orEmpty(), null, null))
            }
        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result

    @NonNull
    @WorkerThread
    abstract suspend fun createCall(): Response<RequestType>

    @WorkerThread
    abstract fun processResponse(response: RequestType): ResultType?
}

data class BodyError(
    val code: String,
    val message: String
)

private fun getErrorMessage(errorBody: ResponseBody?) =
    Gson().fromJson(errorBody?.string(), BodyError::class.java)