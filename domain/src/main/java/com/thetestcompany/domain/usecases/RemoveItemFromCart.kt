package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class RemoveItemFromCart(transformer: Transformer<Boolean>,
                         private val shoppingCartItemRepository: ShoppingCartItemRepository) : UseCase<Boolean>(transformer) {

    companion object {
        private val PARAM_SHOPPING_CART_ITEM_ENTITY = "param:shoppingCartItemEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val shoppingCartItemEntity = data?.get(PARAM_SHOPPING_CART_ITEM_ENTITY)

        shoppingCartItemEntity?.let {
            return Observable.fromCallable {
                val entity = it as ShoppingCartItemEntity
                shoppingCartItemRepository.removeItemFromCart(entity)
                return@fromCallable true
            }
        }?: return Observable.error({IllegalArgumentException("ShoppingCartItemEntity must be provided")})
    }

    fun remove(shoppingCartItemEntity: ShoppingCartItemEntity): Observable<Boolean> {
        val data = HashMap<String, ShoppingCartItemEntity>()
        data[PARAM_SHOPPING_CART_ITEM_ENTITY] = shoppingCartItemEntity
        return observable(data)
    }
}