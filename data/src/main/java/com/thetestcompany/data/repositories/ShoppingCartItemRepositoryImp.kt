package com.thetestcompany.data.repositories

import com.thetestcompany.data.caches.InMemoryShoppingCartCache
import com.thetestcompany.data.entities.ShoppingCartItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import io.reactivex.Observable


class ShoppingCartItemRepositoryImp(private val memoryCache: InMemoryShoppingCartCache,
                                    private val dataMapper: Mapper<ShoppingCartItemData, ShoppingCartItemEntity>,
                                    private val entityMapper: Mapper<ShoppingCartItemEntity, ShoppingCartItemData>): ShoppingCartItemRepository {

    override fun getItemsInCart(): Observable<List<ShoppingCartItemEntity>> {
        return dataMapper.observable(memoryCache.getCart())
    }

    override fun addItemToCart(shoppingCartItemEntity: ShoppingCartItemEntity) {
        memoryCache.add(entityMapper.mapFrom(shoppingCartItemEntity))
    }

    override fun removeItemFromCart(shoppingCartItemEntity: ShoppingCartItemEntity) {
        memoryCache.remove(entityMapper.mapFrom(shoppingCartItemEntity))
    }

    override fun clearShoppingCart() {
        memoryCache.clear()
    }
}