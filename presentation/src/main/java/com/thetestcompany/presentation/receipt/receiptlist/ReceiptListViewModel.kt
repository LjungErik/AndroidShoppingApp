package com.thetestcompany.presentation.receipt.receiptlist

import android.arch.lifecycle.MutableLiveData
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptEntity
import com.thetestcompany.domain.usecases.GetReceipts
import com.thetestcompany.presentation.common.BaseViewModel
import com.thetestcompany.presentation.common.SingleLiveEvent
import com.thetestcompany.presentation.entities.Receipt


class ReceiptListViewModel(private val getReceipts: GetReceipts,
                           private val entityMapper: Mapper<ReceiptEntity, Receipt>): BaseViewModel() {

    val viewState: MutableLiveData<ReceiptListViewState> = MutableLiveData()
    val errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = ReceiptListViewState()
    }

    fun getReceipts() {
        addDisposable(getReceipts.observable()
                .flatMap { entityMapper.observable(it) }
                .subscribe({ receipts ->
                    viewState.value?.let {
                        val newState = viewState.value?.copy(
                                receipts = receipts
                        )
                        viewState.value = newState
                        errorState.value = null
                    }
                }, {
                    errorState.value = it
                }))
    }
}