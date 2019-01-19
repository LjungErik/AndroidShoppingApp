package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.entities.ShoppingCartItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class AddItemToCart(transformer: Transformer<ShoppingCartItemEntity>,
                    private val shoppingCartItemRepository: ShoppingCartItemRepository) : UseCase<ShoppingCartItemEntity>(transformer) {

    companion object {
        private val PARAM_CART_ITEM_ENTITY = "param:cartItemEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ShoppingCartItemEntity> {
        val cartItemEntity = data?.get(PARAM_CART_ITEM_ENTITY)

        cartItemEntity?.let {
            val entity = it as ShoppingCartItemEntity
            return Observable.fromCallable {
                shoppingCartItemRepository.addItemToCart(entity)
                return@fromCallable entity
            }

        } ?: return Observable.error({IllegalArgumentException("ShoppingCartItemEntity must be provided")})
    }

    fun add(cartItemEntity: ShoppingCartItemEntity): Observable<ShoppingCartItemEntity> {
        val data = HashMap<String, ShoppingCartItemEntity>()
        data[PARAM_CART_ITEM_ENTITY] = cartItemEntity
        return observable(data)
    }

}