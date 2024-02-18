package com.example.jetweatherforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "measurement_tbl")
data class Measurement(
    @Nonnull
    @PrimaryKey
    val measurement:String
)
