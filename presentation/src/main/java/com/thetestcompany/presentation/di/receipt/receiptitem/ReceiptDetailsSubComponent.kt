package com.thetestcompany.presentation.di.receipt.receiptitem

import com.thetestcompany.presentation.receipt.receiptdetails.ReceiptDetailsFragment
import dagger.Subcomponent

@Subcomponent(modules = [ReceiptDetailsModule::class])
interface ReceiptDetailsSubComponent {
    fun inject(receiptDetailsFragment: ReceiptDetailsFragment)
}