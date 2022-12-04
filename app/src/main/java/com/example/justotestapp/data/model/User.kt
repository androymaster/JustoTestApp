package com.example.justotestapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val first: String?,
    val last: String?,
    val location: String?,
    val state: String?,
    val thumbnail: String?,
    val latitude: String?,
    val longitude: String?,
    val id: Int = 0
): Parcelable

data class UserList(val results: List<User> = listOf())