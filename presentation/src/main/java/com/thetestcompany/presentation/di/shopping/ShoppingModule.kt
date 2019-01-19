package com.thetestcompany.presentation.di.shopping

import com.thetestcompany.domain.ProductsInStoreRepository
import com.thetestcompany.domain.ShoppingCartItemRepository
import com.thetestcompany.domain.usecases.*
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.mappers.ShoppingCartEntityItemMapper
import com.thetestcompany.presentation.mappers.ShoppingCartItemEntityMapper
import com.thetestcompany.presentation.shopping.ShoppingVMFactory
import dagger.Module
import dagger.Provides


@Module
class ShoppingModule {

    @Provides
    fun provideGetItemsInCart(shoppingCartItemRepository: ShoppingCartItemRepository): GetItemsInCart {
        return GetItemsInCart(AsyncTransformer(), shoppingCartItemRepository)
    }

    @Provides
    fun provideAddItemToCart(shoppingCartItemRepository: ShoppingCartItemRepository): AddItemToCart {
        return AddItemToCart(AsyncTransformer(), shoppingCartItemRepository)
    }

    @Provides
    fun provideRemoveItemFromCart(shoppingCartItemRepository: ShoppingCartItemRepository): RemoveItemFromCart {
        return RemoveItemFromCart(AsyncTransformer(), shoppingCartItemRepository)
    }

    @Provides
    fun provideGetProductFromBarcodes(productsInStoreRepository: ProductsInStoreRepository) : GetProductFromBarcodes {
        return GetProductFromBarcodes(AsyncTransformer(), productsInStoreRepository)
    }

    @Provides
    fun clearShoppingCart(shoppingCartItemRepository: ShoppingCartItemRepository): ClearShoppingCart {
        return ClearShoppingCart(AsyncTransformer(), shoppingCartItemRepository)
    }

    @Provides
    fun provideShoppingVMFactory(getItemsInCart: GetItemsInCart,
                                 addItemToCart: AddItemToCart,
                                 removeItemFromCart: RemoveItemFromCart,
                                 getProductFromBarcodes: GetProductFromBarcodes,
                                 clearShoppingCart: ClearShoppingCart): ShoppingVMFactory {
        return ShoppingVMFactory(
                getItemsInCart,
                addItemToCart,
                removeItemFromCart,
                getProductFromBarcodes,
                clearShoppingCart,
                ShoppingCartEntityItemMapper(),
                ShoppingCartItemEntityMapper())
    }
}