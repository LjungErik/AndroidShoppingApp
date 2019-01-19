package com.thetestcompany.presentation.di.shopping.shoppingtobuy

import com.thetestcompany.domain.ShoppingToBuyItemRepository
import com.thetestcompany.domain.usecases.GetItemsToBuy
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.mappers.ShoppingToBuyEntityItemMapper
import com.thetestcompany.presentation.shopping.shoppingtobuy.ShoppingToBuyVMFactory
import dagger.Module
import dagger.Provides


@Module
class ShoppingToBuyModule {

    @Provides
    fun provideGetItemsToBuy(shoppingToBuyItemRepository: ShoppingToBuyItemRepository): GetItemsToBuy {
        return GetItemsToBuy(AsyncTransformer(), shoppingToBuyItemRepository)
    }

    @Provides
    fun provideShoppingToBuyVMFactory(getItemsToBuy: GetItemsToBuy): ShoppingToBuyVMFactory {
        return ShoppingToBuyVMFactory(getItemsToBuy, ShoppingToBuyEntityItemMapper())
    }
}