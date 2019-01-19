package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingListRepository
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class RemoveShoppingLists(transformer: Transformer<Boolean>,
                          private val shoppingListRepository: ShoppingListRepository) : UseCase<Boolean>(transformer) {

    companion object {
        private val PARAM_SHOPPING_LIST_ENTITIES = "param:shoppingListEntities"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val shoppingListEntities = data?.get(PARAM_SHOPPING_LIST_ENTITIES)

        shoppingListEntities?.let {
            return Observable.fromCallable {
                val entities = it as List<ShoppingListEntity>
                shoppingListRepository.deleteShoppingLists(entities)
                return@fromCallable true
            }
        }?: return Observable.error({IllegalArgumentException("ShoppingListEntities must be provided")})

    }

    fun remove(shoppingListEntities: List<ShoppingListEntity>): Observable<Boolean> {
        val data = HashMap<String, List<ShoppingListEntity>>()
        data[PARAM_SHOPPING_LIST_ENTITIES] = shoppingListEntities
        return observable(data)
    }

}