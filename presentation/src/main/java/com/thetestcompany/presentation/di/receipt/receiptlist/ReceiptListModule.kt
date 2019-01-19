package com.thetestcompany.presentation.di.receipt.receiptlist

import com.thetestcompany.domain.ReceiptRepository
import com.thetestcompany.domain.usecases.GetReceipts
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.mappers.ReceiptEntityToReceiptMapper
import com.thetestcompany.presentation.mappers.ReceiptToReceiptEntityMapper
import com.thetestcompany.presentation.receipt.receiptlist.ReceiptListVMFactory
import dagger.Module
import dagger.Provides

@Module
class ReceiptListModule {

    @Provides
    fun provideGetReceipts(receiptRepository: ReceiptRepository): GetReceipts {
        return GetReceipts(AsyncTransformer(), receiptRepository)
    }

    @Provides
    fun provideReceiptListVMFactory(getReceipts: GetReceipts,
                                    entityMapper: ReceiptEntityToReceiptMapper,
                                    itemMapper: ReceiptToReceiptEntityMapper): ReceiptListVMFactory {
        return ReceiptListVMFactory(
                getReceipts,
                entityMapper)
    }
}