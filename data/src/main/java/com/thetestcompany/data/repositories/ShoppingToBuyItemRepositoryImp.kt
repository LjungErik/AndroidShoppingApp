package com.thetestcompany.data.repositories

import com.thetestcompany.data.caches.InMemoryShoppingCartCache
import com.thetestcompany.data.db.ShoppingListItemDao
import com.thetestcompany.data.entities.ShoppingCartItemData
import com.thetestcompany.data.entities.ShoppingToBuyItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ShoppingToBuyItemRepository
import com.thetestcompany.domain.entities.ShoppingToBuyItemEntity
import io.reactivex.Observable


class ShoppingToBuyItemRepositoryImp(private val inMemoryShoppingCartCache: InMemoryShoppingCartCache,
                                     private val shoppingListItemDao: ShoppingListItemDao,
                                     private val dataMapper: Mapper<ShoppingToBuyItemData, ShoppingToBuyItemEntity>): ShoppingToBuyItemRepository {

    private var cachedShoppingListItems: List<ShoppingToBuyItemData>? = null

    override fun getToBuyItems(listNames: Array<String>): Observable<List<ShoppingToBuyItemEntity>> {
        return Observable.fromCallable {
            if(cachedShoppingListItems == null) {
                cachedShoppingListItems = shoppingListItemDao.getGroupedListItems(listNames)
            }
            return@fromCallable updateToBuyWithItemsFromCart(cachedShoppingListItems!!, inMemoryShoppingCartCache.getCart())
                                .map { dataMapper.mapFrom(it) }
        }
    }

    private fun updateToBuyWithItemsFromCart(tobuyList: List<ShoppingToBuyItemData>, cartItems: List<ShoppingCartItemData>): List<ShoppingToBuyItemData> {
        tobuyList.forEach{it.boughtQuantity = 0.0}

        for(item in cartItems) {
            var index = tobuyList.indexOfFirst { it.equalsCartItem(item) }
            tobuyList[index].boughtQuantity += item.quantity
        }
        return tobuyList
    }

}