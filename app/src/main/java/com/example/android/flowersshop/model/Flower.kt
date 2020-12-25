package com.example.android.flowersshop.model


data class Flower(
    var name: String = "",
        var price: Int = 0,
        var photoUrl: String = "",
        var isFavorite: Boolean = false,
        var isBuy: Boolean = false,
        var isBouquet: Boolean = false,
        var quantity: Int = 1
) {
    constructor() : this("",
        0,
        "",
        false,
        false,
        false,
            1
    )
}