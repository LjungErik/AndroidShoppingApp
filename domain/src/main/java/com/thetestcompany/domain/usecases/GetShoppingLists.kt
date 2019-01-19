package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingListRepository
import com.thetestcompany.domain.entities.ShoppingListEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class GetShoppingLists(transformer: Transformer<List<ShoppingListEntity>>,
                       private val shoppingListRepository: ShoppingListRepository) : UseCase<List<ShoppingListEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ShoppingListEntity>> {
        return shoppingListRepository.getShoppingLists()
    }

}