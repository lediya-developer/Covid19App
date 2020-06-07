package com.lediya.covid19app.model

import com.google.gson.annotations.SerializedName

data class ContactDetails(
    @SerializedName("helpline_number")
    val helpLine:String,
    @SerializedName("state_or_UT")
    val state:String
)