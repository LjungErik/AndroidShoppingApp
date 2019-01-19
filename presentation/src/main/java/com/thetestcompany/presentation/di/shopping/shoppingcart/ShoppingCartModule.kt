package com.thetestcompany.presentation.di.shopping.shoppingcart

import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.usecases.GetItemsInCart
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.mappers.ShoppingCartEntityItemMapper
import com.thetestcompany.presentation.shopping.shoppingcart.ShoppingCartVMFactory
import com.thetestcompany.presentation.shopping.shoppingcart.ShoppingCartViewModel
import dagger.Module
import dagger.Provides


@Module
class ShoppingCartModule {

    @Provides
    fun provideGetItemsInCart(shoppingCartItemRepository: ShoppingCartItemRepository): GetItemsInCart {
        return GetItemsInCart(AsyncTransformer(), shoppingCartItemRepository)
    }

    @Provides
    fun provideShoppingCartVMFactory(getItemsInCart: GetItemsInCart): ShoppingCartVMFactory {
        return ShoppingCartVMFactory(getItemsInCart, ShoppingCartEntityItemMapper())
    }
}