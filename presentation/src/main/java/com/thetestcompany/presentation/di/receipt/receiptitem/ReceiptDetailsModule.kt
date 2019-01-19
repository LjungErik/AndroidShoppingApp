package com.thetestcompany.presentation.di.receipt.receiptitem

import com.thetestcompany.domain.ReceiptDetailsRepository
import com.thetestcompany.domain.usecases.GetReceiptItems
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.mappers.ReceiptItemEntityToItemMapper
import com.thetestcompany.presentation.receipt.receiptdetails.ReceiptDetailsVMFactory
import dagger.Module
import dagger.Provides

@Module
class ReceiptDetailsModule {

    @Provides
    fun provideGetReceiptItems(receiptDetailsRepository: ReceiptDetailsRepository): GetReceiptItems {
        return GetReceiptItems(AsyncTransformer(), receiptDetailsRepository)
    }

    @Provides
    fun provideReceiptDetailsVMFactory(getReceiptItems: GetReceiptItems,
                                       entityMapper: ReceiptItemEntityToItemMapper): ReceiptDetailsVMFactory {
        return ReceiptDetailsVMFactory(
                getReceiptItems,
                entityMapper
        )
    }
}