package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingListItemRepository
import com.thetestcompany.domain.entities.ShoppingListItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class GetShoppingListItems(transformer: Transformer<List<ShoppingListItemEntity>>,
                           private val shoppingListItemRepository: ShoppingListItemRepository) : UseCase<List<ShoppingListItemEntity>>(transformer) {

    companion object {
        private val PARAM_LIST_NAME = "param:listName"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ShoppingListItemEntity>> {
        val listName = data?.get(PARAM_LIST_NAME)

        return listName?.let {
            val name = it as String
            return shoppingListItemRepository.getShoppingItems(name)
        } ?: return Observable.error({IllegalArgumentException("List name must be provided")})
    }

    fun get(listName: String): Observable<List<ShoppingListItemEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_LIST_NAME] = listName
        return observable(data)
    }
}