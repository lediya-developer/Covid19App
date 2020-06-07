package com.lediya.covid19app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lediya.covid19app.communication.ApiEndPointService
import com.lediya.covid19app.communication.RestClient
import com.lediya.covid19app.communication.errorhandler.ResponseHandler
import com.lediya.covid19app.communication.errorhandler.Status
import kotlinx.coroutines.Dispatchers

class SummaryViewModel(application: Application) : AndroidViewModel(application) {
    fun downloadIndiaCurrentData()  = liveData(Dispatchers.IO) {

        emit(ResponseHandler.loading())
        val api = RestClient.createService(ApiEndPointService::class.java)
            try {
                val response = api.getSummaryDataAsync()
                emit(ResponseHandler.success(response.body()))
            } catch (e:Exception) {
                emit(ResponseHandler.error(message = e.message ?: "Error Occurred!"))
            }

    }
    fun downloadStateWiseData()  = liveData(Dispatchers.IO) {
        emit(ResponseHandler.loading())
        val api = RestClient.createService(ApiEndPointService::class.java)
        try {
            val response = api.getCountryDataAsync()
            emit(ResponseHandler.success(response.body()))
        } catch (e:Exception) {
            emit(ResponseHandler.error(message = e.message ?: "Error Occurred!"))
        }
    }
    fun downloadHelpLineData()  = liveData(Dispatchers.IO) {
        emit(ResponseHandler.loading())
        val api = RestClient.createHelpService(ApiEndPointService::class.java)

        try {
            val response = api.getHelpLineDataAsync()
            emit(ResponseHandler.success(response.body()))
        } catch (e:Exception) {
        emit(ResponseHandler.error(message = e.message ?: "Error Occurred!"))
         }
    }
}