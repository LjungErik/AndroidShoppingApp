package com.thetestcompany.domain

import com.thetestcompany.domain.entities.Optional
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import io.reactivex.Observable


interface ProductsInStoreRepository {
    fun getProductWithBarcode(barcodes: List<String>): Observable<Optional<ShoppingCartItemEntity>>
}