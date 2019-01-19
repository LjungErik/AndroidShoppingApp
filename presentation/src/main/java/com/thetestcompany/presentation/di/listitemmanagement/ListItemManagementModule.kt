package com.thetestcompany.presentation.di.listitemmanagement

import com.thetestcompany.domain.ShoppingListItemRepository
import com.thetestcompany.domain.usecases.AddShoppingListItem
import com.thetestcompany.domain.usecases.GetShoppingListItems
import com.thetestcompany.domain.usecases.RemoveShoppingListItem
import com.thetestcompany.domain.usecases.UpdateShoppingListItem
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.listitemmanagement.ListItemManagementVMFactory
import com.thetestcompany.presentation.mappers.ShoppingEntityItemMapper
import com.thetestcompany.presentation.mappers.ShoppingItemEntityMapper
import dagger.Module
import dagger.Provides

@Module
class ListItemManagementModule {

    @Provides
    fun provideGetShoppingListItemsUseCase(shoppingListItemRepository: ShoppingListItemRepository): GetShoppingListItems {
        return GetShoppingListItems(AsyncTransformer(), shoppingListItemRepository)
    }

    @Provides
    fun provideRemoveShoppingListItemUseCase(shoppingListItemRepository: ShoppingListItemRepository): RemoveShoppingListItem {
        return RemoveShoppingListItem(AsyncTransformer(), shoppingListItemRepository)
    }

    @Provides
    fun provideUpdateShoppingListItemUseCase(shoppingListItemRepository: ShoppingListItemRepository): UpdateShoppingListItem {
        return UpdateShoppingListItem(AsyncTransformer(), shoppingListItemRepository)
    }

    @Provides
    fun provideAddShoppingListItemUseCase(shoppingListItemRepository: ShoppingListItemRepository): AddShoppingListItem {
        return AddShoppingListItem(AsyncTransformer(), shoppingListItemRepository)
    }

    @Provides
    fun provideListItemManagementVMFactory(
            getShoppingListItems: GetShoppingListItems,
            removeShoppingListItem: RemoveShoppingListItem,
            updateShoppingListItem: UpdateShoppingListItem,
            addShoppingListItem: AddShoppingListItem,
            entityMapper: ShoppingEntityItemMapper,
            itemMapper: ShoppingItemEntityMapper
    ) : ListItemManagementVMFactory {
        return ListItemManagementVMFactory(getShoppingListItems, removeShoppingListItem, updateShoppingListItem, addShoppingListItem, entityMapper, itemMapper)
    }
}