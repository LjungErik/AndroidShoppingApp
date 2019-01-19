package com.thetestcompany.presentation.di.receipt.receiptlist

import com.thetestcompany.presentation.receipt.receiptlist.ReceiptListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ReceiptListModule::class])
interface ReceiptListSubComponent {
    fun inject(receiptListFragment: ReceiptListFragment)
}