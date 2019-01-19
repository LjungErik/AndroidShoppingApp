package com.thetestcompany.presentation.di.checkout

import com.thetestcompany.domain.ReceiptDetailsRepository
import com.thetestcompany.domain.ReceiptRepository
import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.usecases.CreateReceiptFromCart
import com.thetestcompany.domain.usecases.GetItemsInCart
import com.thetestcompany.presentation.checkout.CheckoutVMFactory
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.mappers.ShoppingCartEntityItemMapper
import dagger.Module
import dagger.Provides

@Module
class CheckoutModule {

    @Provides
    fun provideGetItemsInCart(shoppingCartItemRepository: ShoppingCartItemRepository): GetItemsInCart {
        return GetItemsInCart(AsyncTransformer(), shoppingCartItemRepository)
    }

    @Provides
    fun provideCreateReceiptFromCart(shoppingCartItemRepository: ShoppingCartItemRepository,
                                     receiptRepository: ReceiptRepository,
                                     receiptDetailsRepository: ReceiptDetailsRepository): CreateReceiptFromCart {
        return CreateReceiptFromCart(
                AsyncTransformer(),
                shoppingCartItemRepository,
                receiptRepository,
                receiptDetailsRepository)
    }

    @Provides
    fun prodiveCheckoutVMFactory(getItemsInCart: GetItemsInCart, createReceiptFromCart: CreateReceiptFromCart, entityMapper: ShoppingCartEntityItemMapper) : CheckoutVMFactory {
        return CheckoutVMFactory(
                getItemsInCart,
                createReceiptFromCart,
                entityMapper
        )
    }
}