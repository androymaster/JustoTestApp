package com.example.justotestapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @ColumnInfo(name = "name") val first: String?,
    @ColumnInfo(name = "last") val last: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "state") val state: String?,
    @ColumnInfo(name = "thumbnail") val thumbnail: String?,
    @ColumnInfo(name = "latitude") val latitude: String?,
    @ColumnInfo(name = "longitude") val longitude: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

fun List<UserEntity>.toUsersList(): UserList{
    val resultList = mutableListOf<User>()
    this.forEach{ userEntity ->
        resultList.add(userEntity.toUser())
    }
    return UserList(resultList)
}

fun UserEntity.toUser(): User = User(
    this.first,
    this.last,
    this.location,
    this.state,
    this.thumbnail,
    this.latitude,
    this.longitude,
    this.id
)

fun User.toUserEntity(): UserEntity = UserEntity(
    this.first,
    this.last,
    this.location,
    this.state,
    this.thumbnail,
    this.latitude,
    this.longitude,
    this.id
)