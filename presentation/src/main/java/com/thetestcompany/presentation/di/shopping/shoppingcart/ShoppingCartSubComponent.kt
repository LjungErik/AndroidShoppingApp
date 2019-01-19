package com.thetestcompany.presentation.di.shopping.shoppingcart

import com.thetestcompany.presentation.shopping.shoppingcart.ShoppingCartFragment
import dagger.Subcomponent


@Subcomponent(modules = [ShoppingCartModule::class])
interface ShoppingCartSubComponent {
    fun inject(shoppingCartFragment: ShoppingCartFragment)
}