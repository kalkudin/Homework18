package com.example.homework18.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//serialized name isnt working ;-;
@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val email: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val avatar: String
)
