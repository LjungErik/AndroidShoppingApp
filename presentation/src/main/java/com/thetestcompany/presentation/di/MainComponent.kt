package com.thetestcompany.presentation.di

import com.thetestcompany.presentation.di.checkout.CheckoutModule
import com.thetestcompany.presentation.di.checkout.CheckoutSubComponent
import com.thetestcompany.presentation.di.listitemmanagement.ListItemManagementModule
import com.thetestcompany.presentation.di.listitemmanagement.ListItemManagementSubComponent
import com.thetestcompany.presentation.di.listmanagement.ListManagementModule
import com.thetestcompany.presentation.di.listmanagement.ListManagementSubComponent
import com.thetestcompany.presentation.di.modules.AppModule
import com.thetestcompany.presentation.di.modules.DataModule
import com.thetestcompany.presentation.di.modules.NetworkModule
import com.thetestcompany.presentation.di.receipt.receiptitem.ReceiptDetailsModule
import com.thetestcompany.presentation.di.receipt.receiptitem.ReceiptDetailsSubComponent
import com.thetestcompany.presentation.di.receipt.receiptlist.ReceiptListModule
import com.thetestcompany.presentation.di.receipt.receiptlist.ReceiptListSubComponent
import com.thetestcompany.presentation.di.selectlistforshopping.SelectListForShoppingModule
import com.thetestcompany.presentation.di.selectlistforshopping.SelectListForShoppingSubComponent
import com.thetestcompany.presentation.di.shopping.ShoppingModule
import com.thetestcompany.presentation.di.shopping.ShoppingSubComponent
import com.thetestcompany.presentation.di.shopping.shoppingcart.ShoppingCartModule
import com.thetestcompany.presentation.di.shopping.shoppingcart.ShoppingCartSubComponent
import com.thetestcompany.presentation.di.shopping.shoppingtobuy.ShoppingToBuyModule
import com.thetestcompany.presentation.di.shopping.shoppingtobuy.ShoppingToBuySubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    DataModule::class
])
interface MainComponent {
    fun plus(listManagementModule: ListManagementModule): ListManagementSubComponent
    fun plus(listItemManagementModule: ListItemManagementModule): ListItemManagementSubComponent
    fun plus(selectListForShoppingModule: SelectListForShoppingModule): SelectListForShoppingSubComponent
    fun plus(shoppingModule: ShoppingModule): ShoppingSubComponent
    fun plus(shoppingCartModule: ShoppingCartModule): ShoppingCartSubComponent
    fun plus(shoppingToBuyModule: ShoppingToBuyModule): ShoppingToBuySubComponent
    fun plus(checkoutModule: CheckoutModule): CheckoutSubComponent
    fun plus(receiptDetailsModule: ReceiptDetailsModule): ReceiptDetailsSubComponent
    fun plus(receiptListModule: ReceiptListModule): ReceiptListSubComponent
}