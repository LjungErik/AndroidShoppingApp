package com.thetestcompany.presentation.di.shopping.shoppingtobuy

import com.thetestcompany.presentation.shopping.shoppingtobuy.ShoppingToBuyFragment
import dagger.Subcomponent


@Subcomponent(modules = [ShoppingToBuyModule::class])
interface ShoppingToBuySubComponent {
    fun inject(shoppingCartFragment: ShoppingToBuyFragment)
}