package com.thetestcompany.presentation.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.thetestcompany.data.caches.InMemoryShoppingCartCache
import com.thetestcompany.data.db.ShoppingDatabase
import com.thetestcompany.data.mappers.*
import com.thetestcompany.data.networkservice.StoreApi
import com.thetestcompany.data.repositories.*
import com.thetestcompany.domain.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class DataModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): ShoppingDatabase {
        return Room.databaseBuilder(context, ShoppingDatabase::class.java,"shopping-db")
                .build()
    }

    @Provides
    @Singleton
    fun provideShoppingListRepository(database: ShoppingDatabase,
                                      dataMapper: ShoppingListDataEntityMapper,
                                      entityMapper: ShoppingListEntityDataMapper): ShoppingListRepository {
        return ShoppingListRepositoryImp(database.shoppingListDao(), dataMapper, entityMapper)
    }

    @Provides
    @Singleton
    fun provideShoppingListItemRepository(database: ShoppingDatabase,
                                          dataMapper: ShoppingListItemDataEntitiyMapper,
                                          entityMapper: ShoppingListItemEntityDataMapper): ShoppingListItemRepository {
        return ShoppingListItemRepositoryImp(database.shoppingListItemDao(), dataMapper, entityMapper)
    }

    @Provides
    @Singleton
    fun provideInMemoryShoppingCartCache(): InMemoryShoppingCartCache {
        return InMemoryShoppingCartCache()
    }

    @Provides
    @Singleton
    fun provideShoppingCartItemRepository(dataMapper: ShoppingCartDataEntityMapper,
                                          entityMapper: ShoppingCartEntityDataMapper,
                                          inMemoryShoppingCartCache: InMemoryShoppingCartCache): ShoppingCartItemRepository {
        return ShoppingCartItemRepositoryImp(inMemoryShoppingCartCache, dataMapper, entityMapper)
    }

    @Provides
    fun provideShoppingToBuyItemRepository(dataMapper: ShoppingToBuyDataEntityMapper,
                                           inMemoryShoppingCartCache: InMemoryShoppingCartCache,
                                           database: ShoppingDatabase): ShoppingToBuyItemRepository {
        return ShoppingToBuyItemRepositoryImp(inMemoryShoppingCartCache, database.shoppingListItemDao(), dataMapper)
    }

    @Provides
    @Singleton
    fun provideProductInStoreRepository(storeApi: StoreApi,
                                        productMapper: ProductToShoppingCartEntityMapper): ProductsInStoreRepository {
        return ProductsInStoreRepositoryImp(storeApi, productMapper)
    }

    @Provides
    @Singleton
    fun provideReceiptDetailsRepository(database: ShoppingDatabase,
                                        dataMapper: ReceiptItemDataEntityMapper,
                                        entityMapper: ReceiptItemEntityDataMapper): ReceiptDetailsRepository {
        return ReceiptDetailsRepositoryImp(database.receiptItemDao(), dataMapper, entityMapper)
    }

    @Provides
    @Singleton
    fun provideReceiptRepository(database: ShoppingDatabase,
                                        dataMapper: ReceiptDataToReceiptEntityMapper,
                                        entityMapper: ReceiptEntityToReceiptDataMapper): ReceiptRepository {
        return ReceiptRepositoryImp(database.receiptDao(), dataMapper, entityMapper)
    }
}