package com.thetestcompany.data.repositories

import com.thetestcompany.data.entities.ProductData
import com.thetestcompany.data.entities.ShoppingCartItemData
import com.thetestcompany.data.networkservice.LookupRequest
import com.thetestcompany.data.networkservice.StoreApi
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ProductsInStoreRepository
import com.thetestcompany.domain.entities.Optional
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import io.reactivex.Observable

class ProductsInStoreRepositoryImp(private val storeApi: StoreApi,
                                   private val productMapper: Mapper<ProductData, ShoppingCartItemEntity>) : ProductsInStoreRepository {

    override fun getProductWithBarcode(barcodes: List<String>): Observable<Optional<ShoppingCartItemEntity>> {
        return storeApi.lookupBarcode(LookupRequest(barcodes)).map { data ->
            data.product?.let {
                Optional(productMapper.mapFrom(it))
            } ?: Optional.empty()
        }
    }

}