package com.lediya.covid19app.communication

import com.lediya.covid19app.model.IndiaSummary
import com.lediya.covid19app.model.StateData
import com.lediya.covid19app.model.StateHelpLine
import retrofit2.Response
import retrofit2.http.GET


interface ApiEndPointService {
    @GET("summary")
    suspend fun getSummaryDataAsync(): Response<IndiaSummary>
    @GET("states")
    suspend fun getCountryDataAsync(): Response<StateData>
    @GET("helpline_numbers")
    suspend fun getHelpLineDataAsync(): Response<StateHelpLine>

}