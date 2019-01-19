package com.thetestcompany.presentation.di.shopping

import com.thetestcompany.presentation.shopping.ShoppingActivity
import dagger.Subcomponent


@Subcomponent(modules = [ShoppingModule::class])
interface ShoppingSubComponent {
    fun inject(shoppingActivity: ShoppingActivity)
}