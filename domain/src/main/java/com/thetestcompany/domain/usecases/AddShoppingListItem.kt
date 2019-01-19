package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingListItemRepository
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class AddShoppingListItem(transformer: Transformer<ShoppingListItemEntity>,
                          private val shoppingListItemRepository: ShoppingListItemRepository) : UseCase<ShoppingListItemEntity>(transformer) {

    companion object {
        private val PARAM_SHOPPING_LIST_ITEM_ENITITY = "param:shoppingListItemEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ShoppingListItemEntity> {
        val shoppingListItemEntity = data?.get(PARAM_SHOPPING_LIST_ITEM_ENITITY)

        shoppingListItemEntity?.let {
            val entity = it as ShoppingListItemEntity
            return shoppingListItemRepository.insertShoppingItem(entity)
                    .map { id ->
                        entity.id = id
                        entity
                    }
        }?: return Observable.error({IllegalArgumentException("ShoppingListItemEntity must be provided")})
    }

    fun add(shoppingListItemEntity: ShoppingListItemEntity) : Observable<ShoppingListItemEntity> {
        val data = HashMap<String, ShoppingListItemEntity>()
        data[PARAM_SHOPPING_LIST_ITEM_ENITITY] = shoppingListItemEntity
        return observable(data)
    }
}