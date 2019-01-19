package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class GetItemsInCart(transformer: Transformer<List<ShoppingCartItemEntity>>,
                     private val shoppingCartItemRepository: ShoppingCartItemRepository) : UseCase<List<ShoppingCartItemEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ShoppingCartItemEntity>> {
        return shoppingCartItemRepository.getItemsInCart()
    }
}