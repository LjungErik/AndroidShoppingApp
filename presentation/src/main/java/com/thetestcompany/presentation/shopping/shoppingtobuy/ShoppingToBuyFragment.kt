package com.thetestcompany.presentation.shopping.shoppingtobuy

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
import java.io.FileDescriptor
import java.io.PrintWriter
import javax.inject.Inject


class ShoppingToBuyFragment: Fragment() {

    @Inject
    lateinit var vmFactory: ShoppingToBuyVMFactory

    private lateinit var viewModel: ShoppingToBuyViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShoppingToBuyAdapter

    private lateinit var listNames: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).createShoppingToBuyComponent().inject(this)

        setupViewModel()
        setupViewState()

        if (arguments != null) {
            listNames = arguments.getStringArray(LISTS_ARG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_shopping_to_buy, container, false)

        setupRecyclerView(rootView)
        requestItemsToBuy()

        return rootView
    }

    override fun onDestroy() {
        (activity.application as App).releaseShoppingToBuyComponent()
        super.onDestroy()
    }

    fun updateView() {
        requestItemsToBuy()
    }

    private fun handleViewState(viewState: ShoppingToBuyViewState) {
        adapter.setItems(viewState.items)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ShoppingToBuyViewModel::class.java)
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
        adapter = ShoppingToBuyAdapter(activity, unitTranslator)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun requestItemsToBuy() {
        viewModel.getItemsToBuy(listNames)
    }

    companion object {

        private val TAG = "ShoppingToBuyFragment"

        private val LISTS_ARG: String = "lists"

        fun newInstance(listNames: Array<String>): ShoppingToBuyFragment {
            val fragment = ShoppingToBuyFragment()
            val args = Bundle()
            args.putStringArray(LISTS_ARG, listNames)
            fragment.arguments = args
            return fragment
        }
    }
}