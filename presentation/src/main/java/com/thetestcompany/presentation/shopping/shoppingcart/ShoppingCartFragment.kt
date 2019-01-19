package com.thetestcompany.presentation.shopping.shoppingcart

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.App
import com.thetestcompany.presentation.common.UnitTranslator
import javax.inject.Inject


class ShoppingCartFragment: Fragment() {

    @Inject
    lateinit var vmFactory: ShoppingCartVMFactory

    private lateinit var viewModel: ShoppingCartViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).createShoppingCartComponent().inject(this)

        setupViewModel()
        setupViewState()

        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_shopping_cart, container, false)

        setupRecyclerView(rootView)

        requestItemsInCart()

        return rootView
    }

    override fun onDestroy() {
        (activity.application as App).releaseShoppingCartComponent()
        super.onDestroy()
    }

    fun updateView() {
        requestItemsInCart()
    }

    private fun handleViewState(viewState: ShoppingCartViewState) {
        adapter.setItems(viewState.items)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ShoppingCartViewModel::class.java)
    }

    private fun setupViewState() {
        viewModel.viewState.observe(this, Observer {
            if(it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer {
            //handle ErrorState updates
            Log.e(TAG, "Unable to perform action", it)
        })
    }

    private fun setupRecyclerView(view: View) {
        val unitTranslator = UnitTranslator(resources)

        recyclerView = view.findViewById(R.id.shopping_tab_rv)
        adapter = ShoppingCartAdapter(activity, unitTranslator)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun requestItemsInCart() {
        viewModel.getItemsInCart()
    }

    companion object {

        private val TAG = "ShoppingCart"

        fun newInstance(): ShoppingCartFragment {
            val fragment = ShoppingCartFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}