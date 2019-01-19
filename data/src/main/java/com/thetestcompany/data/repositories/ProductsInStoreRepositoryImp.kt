package com.thetestcompany.data.repositories

import com.thetestcompany.data.entities.ProductData
import com.thetestcompany.data.entities.ShoppingCartItemData
import com.thetestcompany.data.networkservice.LookupRequest
import com.thetestcompany.data.networkservice.StoreApi
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ProductsInStoreRepository
import com.thetestcompany.domain.entities.Optional
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.entities.UnitsOfQuantity
import io.reactivex.Observable

class ProductsInStoreRepositoryImp(private val productMapper: Mapper<ProductData, ShoppingCartItemEntity>) : ProductsInStoreRepository {

    override fun getProductWithBarcode(barcodes: List<String>): Observable<Optional<ShoppingCartItemEntity>> {

        var defaultItem = ShoppingCartItemEntity(
                barcodeId = barcodes.get(0),
                name = "Test product",
                quantity = 1.0,
                unitPrice = 150.0,
                unit = UnitsOfQuantity.ST,
                type = "fruit",
                keywords = arrayOf("fruit", "test"))

        return Observable.fromCallable { Optional.of(defaultItem)}

    }

}