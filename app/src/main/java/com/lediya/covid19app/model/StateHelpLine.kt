package com.lediya.covid19app.model

import com.google.gson.annotations.SerializedName

data class StateHelpLine (
    @SerializedName("contact_details")
    var contactDetails: List<ContactDetails>
)
