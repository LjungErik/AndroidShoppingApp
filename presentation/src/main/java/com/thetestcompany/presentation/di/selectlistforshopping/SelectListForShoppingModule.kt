package com.thetestcompany.presentation.di.selectlistforshopping

import com.thetestcompany.domain.ShoppingListRepository
import com.thetestcompany.domain.usecases.GetShoppingLists
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.mappers.ShoppingEntityListMapper
import com.thetestcompany.presentation.selectlistforshopping.SelectListForShoppingVMFactory
import dagger.Module
import dagger.Provides

@Module
class SelectListForShoppingModule {

    @Provides
    fun provideGetShoppingListsUseCase(shoppingListRepository: ShoppingListRepository): GetShoppingLists {
        return GetShoppingLists(AsyncTransformer(), shoppingListRepository)
    }

    @Provides
    fun provideSelectListForShoppingVMFactory(getShoppingLists: GetShoppingLists,
                                              entityMapper: ShoppingEntityListMapper): SelectListForShoppingVMFactory {
        return SelectListForShoppingVMFactory(getShoppingLists, entityMapper)
    }
}