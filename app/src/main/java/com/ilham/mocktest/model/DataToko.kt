package com.ilham.mocktest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class DataToko(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var nama : String,
    var banyakbarang : String,
    var pemasok : String,
    var tanggal : String
    ) : Serializable
