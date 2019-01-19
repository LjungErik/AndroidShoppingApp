package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class ClearShoppingCart(transformer: Transformer<Boolean>,
                        private val shoppingCartItemRepository: ShoppingCartItemRepository) : UseCase<Boolean>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        return Observable.fromCallable {
            shoppingCartItemRepository.clearShoppingCart()
            return@fromCallable true
        }
    }

}