package com.lediya.covid19app.model

import com.google.gson.annotations.SerializedName

data class IndiaSummary (
    @SerializedName("Active cases")
    val active_case :String,
    @SerializedName("Cured/Discharged/Migrated")
    val cured :String,
    @SerializedName("Death")
    val death:String,
    @SerializedName("Total Cases")
    val total:String
)
