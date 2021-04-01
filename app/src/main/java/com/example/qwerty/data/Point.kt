package com.example.qwerty.data

import android.location.Address
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "points")
data class Point(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val longitide : Double,
    val latitude : Double,
     val adress : String):Parcelable