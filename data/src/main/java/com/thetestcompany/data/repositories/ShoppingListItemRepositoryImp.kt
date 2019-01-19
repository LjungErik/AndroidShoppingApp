package com.thetestcompany.data.repositories

import com.thetestcompany.data.db.ShoppingListItemDao
import com.thetestcompany.data.entities.ShoppingListItemData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ShoppingListItemRepository
import com.thetestcompany.domain.entities.ShoppingListItemEntity

import io.reactivex.Observable


class ShoppingListItemRepositoryImp(private val shoppingListItemDao: ShoppingListItemDao,
                                    private val dataMapper: Mapper<ShoppingListItemData, ShoppingListItemEntity>,
                                    private val entityMapper: Mapper<ShoppingListItemEntity, ShoppingListItemData>) : ShoppingListItemRepository {

    override fun getShoppingItems(listName: String): Observable<List<ShoppingListItemEntity>> {
        return Observable.fromCallable {shoppingListItemDao.getShoppingListItems(listName).map {dataMapper.mapFrom(it)}}
    }

    override fun insertShoppingItem(shoppingListItemEntity: ShoppingListItemEntity): Observable<Long> {
        return Observable.fromCallable {
            shoppingListItemDao.insertShoppingListItem(entityMapper.mapFrom(shoppingListItemEntity))
        }
    }

    override fun deleteShoppingItem(shoppingListItemEntity: ShoppingListItemEntity) {
        shoppingListItemDao.deleteShoppingListItem(entityMapper.mapFrom(shoppingListItemEntity))
    }

    override fun updateShoppingItem(shoppingListItemEntity: ShoppingListItemEntity) {
        shoppingListItemDao.updateShoppingListItem(entityMapper.mapFrom(shoppingListItemEntity))
    }

}