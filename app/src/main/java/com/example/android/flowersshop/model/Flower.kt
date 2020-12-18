package com.example.android.flowersshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers_table")
data class Flower(
        @PrimaryKey
    var name: String = "",
        var price: Int = 0,
        var photoUrl: String = "",
        var isFavorite: Boolean = false,
        var isBuy: Boolean? = false,
        var quantity: Int = 1
) {
    constructor() : this("",
        0,
        "",
        false,
        false,
        1
    )
}