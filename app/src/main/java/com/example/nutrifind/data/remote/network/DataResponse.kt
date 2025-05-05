package com.example.nutrifind.data.remote.network

import com.example.nutrifind.data.remote.model.ApiEdamam


sealed class DataResponse {
    data class Success(val apiEdamam: ApiEdamam?) : DataResponse()
    data class Error(val message: String = "") : DataResponse()
    data object Loading : DataResponse()
}