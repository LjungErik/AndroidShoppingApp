package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingListRepository
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class AddShoppingLists(transformer: Transformer<List<ShoppingListEntity>>,
                      private val shoppingListRepository: ShoppingListRepository) : UseCase<List<ShoppingListEntity>>(transformer) {

    companion object {
        private val PARAM_SHOPPING_LISTS_ENTITY = "param:shoppingListEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ShoppingListEntity>> {
        val shoppingListsEntity = data?.get(PARAM_SHOPPING_LISTS_ENTITY)

        shoppingListsEntity?.let {
            val entities = it as List<ShoppingListEntity>
            return shoppingListRepository.insertShoppingLists(entities)
                    .map{ ids ->
                        entities.zip(ids, {entry, id -> entry.id = id})
                        entities
                    }
        }?: return Observable.error({IllegalArgumentException("ShoppingListEntity must be provided")})
    }

    fun add(shoppingListsEntity: List<ShoppingListEntity>): Observable<List<ShoppingListEntity>> {
        val params = HashMap<String, List<ShoppingListEntity>>()
        params[PARAM_SHOPPING_LISTS_ENTITY] = shoppingListsEntity
        return observable(params)
    }

}