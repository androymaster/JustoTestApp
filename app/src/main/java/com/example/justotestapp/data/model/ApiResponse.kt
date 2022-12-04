package com.example.justotestapp.data.model

data class ApiResponse(
    val results: List<Results> = emptyList()
)

data class Results(
    val name: UserName?,
    val location: UserLocation?,
    val picture: UserPicture?
)

fun ApiResponse.toUser(): UserEntity? {
    return UserEntity(
        first = results.first().name?.first,
        last = results.first().name?.last,
        location = results.first().location?.city,
        state = results.first().location?.state,
        thumbnail = results.first().picture?.thumbnail,
        latitude = results.first().location?.coordinates?.latitude,
        longitude = results.first().location?.coordinates?.longitude
    )
}