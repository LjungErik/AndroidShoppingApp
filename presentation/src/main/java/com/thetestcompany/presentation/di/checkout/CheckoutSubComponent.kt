package com.thetestcompany.presentation.di.checkout

import com.thetestcompany.presentation.checkout.CheckoutActivity
import dagger.Subcomponent

@Subcomponent(modules = [CheckoutModule::class])
interface CheckoutSubComponent {
    fun inject(checkoutActivity: CheckoutActivity)
}