package com.thetestcompany.presentation.di.listmanagement

import com.thetestcompany.domain.ShoppingListRepository
import com.thetestcompany.domain.usecases.AddShoppingList
import com.thetestcompany.domain.usecases.AddShoppingLists
import com.thetestcompany.domain.usecases.GetShoppingLists
import com.thetestcompany.domain.usecases.RemoveShoppingLists
import com.thetestcompany.presentation.common.AsyncTransformer
import com.thetestcompany.presentation.listmanagement.ListManagementVMFactory
import com.thetestcompany.presentation.mappers.ShoppingEntityListMapper
import com.thetestcompany.presentation.mappers.ShoppingListEntityMapper
import dagger.Module
import dagger.Provides

@Module
class ListManagementModule {

    @Provides
    fun provideGetShoppingListsUseCase(shoppingListRepository: ShoppingListRepository): GetShoppingLists {
        return GetShoppingLists(AsyncTransformer(), shoppingListRepository)
    }

    @Provides
    fun provideAddShoppingListUseCase(shoppingListRepository: ShoppingListRepository): AddShoppingList {
        return AddShoppingList(AsyncTransformer(), shoppingListRepository)
    }

    @Provides
    fun provideAddShoppingListsUseCase(shoppingListRepository: ShoppingListRepository): AddShoppingLists {
        return AddShoppingLists(AsyncTransformer(), shoppingListRepository)
    }

    @Provides
    fun provideRemoveShoppingListsUseCase(shoppingListRepository: ShoppingListRepository): RemoveShoppingLists {
        return RemoveShoppingLists(AsyncTransformer(), shoppingListRepository)
    }

    @Provides
    fun provideListManagementVMFactory(
            getShoppingLists: GetShoppingLists,
            addShoppingList: AddShoppingList,
            addShoppingLists: AddShoppingLists,
            removeShoppingLists: RemoveShoppingLists,
            entityMapper: ShoppingEntityListMapper,
            listMapper: ShoppingListEntityMapper): ListManagementVMFactory {
        return ListManagementVMFactory(getShoppingLists, addShoppingList, addShoppingLists, removeShoppingLists, entityMapper, listMapper)
    }
}