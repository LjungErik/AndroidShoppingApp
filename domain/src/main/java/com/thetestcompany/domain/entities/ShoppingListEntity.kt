package com.thetestcompany.domain.entities

import java.util.*


data class ShoppingListEntity(
        var id: Long,
        var name: String,
        var createdAt: Date,
        var color: Int
)