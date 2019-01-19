package com.thetestcompany.presentation.receipt.receiptlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptEntity
import com.thetestcompany.domain.usecases.GetReceipts
import com.thetestcompany.presentation.entities.Receipt


class ReceiptListVMFactory(private val getReceipts: GetReceipts,
                           private val entityMapper: Mapper<ReceiptEntity, Receipt>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReceiptListViewModel::class.java)) {
            return ReceiptListViewModel(
                    getReceipts,
                    entityMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}