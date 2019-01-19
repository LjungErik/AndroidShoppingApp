package com.thetestcompany.presentation.common

import android.app.Application
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.di.DaggerMainComponent
import com.thetestcompany.presentation.di.MainComponent
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

class App: Application() {

    lateinit var mainComponent: MainComponent
    private var listManagementComponent: ListManagementSubComponent? = null
    private var listItemManagementComponent: ListItemManagementSubComponent? = null
    private var selectListForShoppingComponent: SelectListForShoppingSubComponent? = null
    private var shoppingComponent: ShoppingSubComponent? = null
    private var shoppingCartComponent: ShoppingCartSubComponent? = null
    private var shoppingToBuyComponent: ShoppingToBuySubComponent? = null
    private var checkoutComponent: CheckoutSubComponent? = null
    private var receiptDetailsComponent: ReceiptDetailsSubComponent? = null
    private var receiptListComponent: ReceiptListSubComponent? = null

    override fun onCreate() {
        super.onCreate()

        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule(getString(R.string.store_base_url)))
                .dataModule(DataModule())
                .build()
    }

    fun createListManagementComponent(): ListManagementSubComponent {
        listManagementComponent = mainComponent.plus(ListManagementModule())
        return listManagementComponent!!
    }

    fun releaseListManagementComponent() {
        listManagementComponent = null
    }

    fun createListItemManagementComponent(): ListItemManagementSubComponent {
        listItemManagementComponent = mainComponent.plus(ListItemManagementModule())
        return listItemManagementComponent!!
    }

    fun releaseListItemManagementComponent() {
        listItemManagementComponent = null
    }

    fun createSelectListForShoppingComponent(): SelectListForShoppingSubComponent {
        selectListForShoppingComponent = mainComponent.plus(SelectListForShoppingModule())
        return selectListForShoppingComponent!!
    }

    fun releaseSelectListForShoppingComponent() {
        selectListForShoppingComponent = null
    }

    fun createShoppingComponent(): ShoppingSubComponent {
        shoppingComponent = mainComponent.plus(ShoppingModule())
        return shoppingComponent!!
    }

    fun releaseShoppingComponent() {
        shoppingComponent = null
    }

    fun createShoppingCartComponent(): ShoppingCartSubComponent {
        shoppingCartComponent = mainComponent.plus(ShoppingCartModule())
        return shoppingCartComponent!!
    }

    fun releaseShoppingCartComponent() {
        shoppingCartComponent = null
    }

    fun createShoppingToBuyComponent(): ShoppingToBuySubComponent {
        shoppingToBuyComponent = mainComponent.plus(ShoppingToBuyModule())
        return shoppingToBuyComponent!!
    }

    fun releaseShoppingToBuyComponent() {
        shoppingToBuyComponent = null
    }

    fun createCheckoutComponent(): CheckoutSubComponent {
        checkoutComponent = mainComponent.plus(CheckoutModule())
        return checkoutComponent!!
    }

    fun releaseCheckoutComponent() {
        checkoutComponent = null
    }

    fun createReceiptDetailsComponent(): ReceiptDetailsSubComponent {
        receiptDetailsComponent = mainComponent.plus(ReceiptDetailsModule())
        return receiptDetailsComponent!!
    }

    fun releaseReceiptDetailsComponent() {
        receiptDetailsComponent = null
    }

    fun createReceiptListComponent(): ReceiptListSubComponent {
        receiptListComponent = mainComponent.plus(ReceiptListModule())
        return receiptListComponent!!
    }

    fun releaseReceiptListCompoent() {
        receiptListComponent = null
    }
}