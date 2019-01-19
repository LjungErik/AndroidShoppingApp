package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingListRepository
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class AddShoppingList(transformer: Transformer<ShoppingListEntity>,
                      private val shoppingListRepository: ShoppingListRepository) : UseCase<ShoppingListEntity>(transformer) {

    companion object {
        private val PARAM_SHOPPING_LIST_ENTITY = "param:shoppingListEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ShoppingListEntity> {
        val shoppingListEntity = data?.get(PARAM_SHOPPING_LIST_ENTITY)

        shoppingListEntity?.let {
            val entity = it as ShoppingListEntity
            return shoppingListRepository.insertShoppingList(entity)
                    .map { id ->
                        entity.id = id
                        entity
                    }
        }?: return Observable.error({IllegalArgumentException("ShoppingListEntity must be provided")})
    }

    fun add(shoppingListEntity: ShoppingListEntity): Observable<ShoppingListEntity> {
        val params = HashMap<String, ShoppingListEntity>()
        params[PARAM_SHOPPING_LIST_ENTITY] = shoppingListEntity
        return observable(params)
    }

}