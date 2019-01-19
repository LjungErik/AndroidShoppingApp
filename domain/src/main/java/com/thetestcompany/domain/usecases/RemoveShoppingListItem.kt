package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingListItemRepository
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class RemoveShoppingListItem(transformer: Transformer<Boolean>,
                             private val shoppingListItemRepository: ShoppingListItemRepository) : UseCase<Boolean>(transformer) {

    companion object {
        private val PARAM_SHOPPING_LIST_ITEM_ENTITY = "param:shoppingListItemEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val shoppingListItemEntity = data?.get(PARAM_SHOPPING_LIST_ITEM_ENTITY)

        shoppingListItemEntity?.let {
            return Observable.fromCallable {
                val entity = it as ShoppingListItemEntity
                shoppingListItemRepository.deleteShoppingItem(entity)
                return@fromCallable true
            }
        }?: return Observable.error({IllegalArgumentException("ShoppingListItemEntity must be provided")})
    }

    fun remove(shoppingListItemEntity: ShoppingListItemEntity): Observable<Boolean> {
        val data = HashMap<String, ShoppingListItemEntity>()
        data[PARAM_SHOPPING_LIST_ITEM_ENTITY] = shoppingListItemEntity
        return observable(data)
    }

}