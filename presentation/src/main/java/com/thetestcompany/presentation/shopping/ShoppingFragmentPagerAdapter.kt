package com.thetestcompany.presentation.shopping

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.shopping.shoppingcart.ShoppingCartFragment
import com.thetestcompany.presentation.shopping.shoppingtobuy.ShoppingToBuyFragment


class ShoppingFragmentPagerAdapter(private val ctx: Context, fm: FragmentManager, listNames: Array<String>): FragmentPagerAdapter(fm) {

    private val cartFragment: ShoppingCartFragment = ShoppingCartFragment.newInstance()
    private val toBuyFragment: ShoppingToBuyFragment = ShoppingToBuyFragment.newInstance(listNames)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> cartFragment
            else -> toBuyFragment
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> ctx.getString(R.string.tab_cart_title)
            1 -> ctx.getString(R.string.tab_tobuy_title)
            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }

    fun setVisible() {
        cartFragment.updateView()
        toBuyFragment.updateView()
    }

}