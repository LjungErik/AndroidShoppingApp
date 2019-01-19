package com.thetestcompany.presentation.di.selectlistforshopping

import com.thetestcompany.presentation.selectlistforshopping.SelectListForShoppingActivity
import dagger.Subcomponent

@Subcomponent(modules = [SelectListForShoppingModule::class])
interface SelectListForShoppingSubComponent {
    fun inject(selectListForShoppingActivity: SelectListForShoppingActivity)
}