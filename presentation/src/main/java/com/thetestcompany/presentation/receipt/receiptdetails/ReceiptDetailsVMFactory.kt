package com.thetestcompany.presentation.receipt.receiptdetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.thetestcompany.domain.Mapper
import com.thetestcompany.domain.entities.ReceiptItemEntity
import com.thetestcompany.domain.usecases.GetReceiptItems
import com.thetestcompany.presentation.entities.ReceiptItem


class ReceiptDetailsVMFactory(private val getReceiptItems: GetReceiptItems,
                              private val entityMapper: Mapper<ReceiptItemEntity, ReceiptItem>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReceiptDetailsViewModel::class.java)) {
            return ReceiptDetailsViewModel(
                    getReceiptItems,
                    entityMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}