package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ProductsInStoreRepository
import com.thetestcompany.domain.entities.Optional
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable
import java.lang.IllegalArgumentException


class GetProductFromBarcodes(transformer: Transformer<Optional<ShoppingCartItemEntity>>,
                             private val productsInStoreRepository: ProductsInStoreRepository): UseCase<Optional<ShoppingCartItemEntity>>(transformer) {

    companion object {
        private val PARAM_BARCODES = "param:barcodes"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Optional<ShoppingCartItemEntity>> {
        val paramBarcodes = data?.get(PARAM_BARCODES)

        paramBarcodes?.let {
            val barcodes = it as List<String>
            return productsInStoreRepository.getProductWithBarcode(barcodes)
        } ?: return Observable.error({IllegalArgumentException("Barcodes must be provided")})
    }

    fun get(barcodes: List<String>): Observable<Optional<ShoppingCartItemEntity>> {
        val data = HashMap<String, List<String>>()
        data[PARAM_BARCODES] = barcodes
        return observable(data)
    }

}