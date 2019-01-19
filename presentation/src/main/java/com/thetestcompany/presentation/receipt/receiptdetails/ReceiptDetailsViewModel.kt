package com.thetestcompany.presentation.receipt.receiptdetails

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptItemEntity
import com.thetestcompany.domain.usecases.GetReceiptItems
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.ReceiptItem


class ReceiptDetailsViewModel(private val getReceiptItems: GetReceiptItems,
                              private val entityMapper: Mapper<ReceiptItemEntity, ReceiptItem>): BaseViewModel() {

    val viewState: MutableLiveData<ReceiptDetailsViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ReceiptDetailsViewState()
    }

    fun getReceiptItems(receiptId: Long) {
        addDisposable(getReceiptItems.get(receiptId)
                .flatMap { entityMapper.observable(it) }
                .subscribe({ items ->
                    viewState.value?.let {
                        val total = items.sumByDouble { it.totalPrice }
                        val newState = viewState.value?.copy(
                                items = items,
                                total = total
                        )
                        viewState.value = newState
                        errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }
}