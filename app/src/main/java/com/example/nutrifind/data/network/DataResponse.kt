package com.example.nutrifind.data.network

import com.example.nutrifind.data.model.ApiEdamam


sealed class DataResponse {
    data class Success(val apiEdamam: ApiEdamam?) : DataResponse()
    data object Error : DataResponse()
    data object Loading : DataResponse()
}