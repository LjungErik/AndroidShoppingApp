package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ReceiptDetailsRepository
import com.thetestcompany.domain.ReceiptRepository
import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.entities.ReceiptEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class CreateReceiptFromCart(transformer: Transformer<Long>,
                            private val shoppingCartItemRepository: ShoppingCartItemRepository,
                            private val receiptRepository: ReceiptRepository,
                            private val receiptDetailsRepository: ReceiptDetailsRepository): UseCase<Long>(transformer) {

    companion object {
        private val PARAM_ENTITY = "param:entity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Long> {
        val entity = data?.get(PARAM_ENTITY)

        return entity?.let {
            val receiptEntity = it as ReceiptEntity
            return receiptRepository.insertReceipt(receiptEntity)
                    .flatMap { id ->
                        receiptEntity.id = id
                        shoppingCartItemRepository.getItemsInCart()
                    }
                    .flatMap { items ->
                        receiptDetailsRepository.insertCartItems(receiptEntity.id, items)
                    }
                    .flatMap {
                        Observable.fromCallable {
                            shoppingCartItemRepository.clearShoppingCart()
                            receiptEntity.id
                        }
                    }

        } ?: return Observable.error({IllegalArgumentException("Receipt name and location must be provided")})
    }

    fun create(name: String, location: String): Observable<Long> {
        val data = HashMap<String, ReceiptEntity>()
        val entity = ReceiptEntity(id = 0, name = name, location = location)
        data[PARAM_ENTITY] = entity
        return observable(data)
    }
}