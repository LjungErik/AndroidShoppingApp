package com.thetestcompany.domain

import com.thetestcompany.domain.entities.ShoppingToBuyItemEntity
import io.reactivex.Observable


interface ShoppingToBuyItemRepository {
    fun getToBuyItems(listNames: Array<String>): Observable<List<ShoppingToBuyItemEntity>>
}