package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.ShoppingToBuyItemRepository
import com.thetestcompany.domain.entities.ShoppingToBuyItemEntity
import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


class GetItemsToBuy(transformer: Transformer<List<ShoppingToBuyItemEntity>>,
                    private val shoppingToBuyItemRepository: ShoppingToBuyItemRepository): UseCase<List<ShoppingToBuyItemEntity>>(transformer) {

    companion object {
        private val PARAM_LIST_NAMES = "param:listNames"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ShoppingToBuyItemEntity>> {
        val names = data?.get(PARAM_LIST_NAMES)

        names?.let {
            val listNames = it as Array<String>
            return shoppingToBuyItemRepository.getToBuyItems(listNames)
        } ?: return Observable.error({IllegalArgumentException("List names must be provided")})
    }

    fun get(listNames: Array<String>): Observable<List<ShoppingToBuyItemEntity>> {
        val data = HashMap<String, Array<String>>()
        data[PARAM_LIST_NAMES] = listNames
        return observable(data)
    }
}