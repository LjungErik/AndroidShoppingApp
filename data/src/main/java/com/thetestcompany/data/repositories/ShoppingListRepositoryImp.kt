package com.thetestcompany.data.repositories

import com.thetestcompany.data.db.ShoppingListDao
import com.thetestcompany.data.entities.ShoppingListData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.ShoppingListRepository
import com.thetestcompany.domain.entities.ShoppingListEntity
import io.reactivex.Observable


class ShoppingListRepositoryImp(private val shoppingListDao: ShoppingListDao,
                                private val dataMapper: Mapper<ShoppingListData, ShoppingListEntity>,
                                private val entityMapper: Mapper<ShoppingListEntity, ShoppingListData>) : ShoppingListRepository {

    override fun getShoppingLists(): Observable<List<ShoppingListEntity>> {
        return Observable.fromCallable { shoppingListDao.getShoppingLists().map{dataMapper.mapFrom(it)} }
    }

    override fun insertShoppingList(shoppingListEntity: ShoppingListEntity): Observable<Long> {
        return Observable.fromCallable {
            shoppingListDao.insertShoppingList(entityMapper.mapFrom(shoppingListEntity))
        }
    }

    override fun insertShoppingLists(shoppingListsEntity: List<ShoppingListEntity>): Observable<List<Long>> {
        return Observable.fromCallable {
            shoppingListDao.insertShoppingLists(shoppingListsEntity.map { entityMapper.mapFrom(it) })
        }
    }

    override fun deleteShoppingLists(shoppingListEntities: List<ShoppingListEntity>) {
        shoppingListDao.deleteShoppingLists(
                shoppingListEntities.map {
                    entityMapper.mapFrom(it)
                }
        )
    }

}