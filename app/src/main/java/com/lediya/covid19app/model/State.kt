package com.lediya.covid19app.model

data class State(
    val _id:String,
    val name:String,
    val active:Int,
    val cured:Int,
    val death:Int,
    val total:Int
)