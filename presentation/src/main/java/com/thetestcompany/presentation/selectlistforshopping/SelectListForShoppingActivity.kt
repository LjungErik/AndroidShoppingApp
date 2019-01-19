package com.thetestcompany.presentation.selectlistforshopping

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.App
import com.thetestcompany.presentation.shopping.ShoppingActivity
import javax.inject.Inject

class SelectListForShoppingActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: SelectListForShoppingVMFactory

    private lateinit var viewModel: SelectListForShoppingViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var nextBtn: FloatingActionButton
    private lateinit var toolbar: Toolbar

    private lateinit var adapter: SelectListForShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).createSelectListForShoppingComponent().inject(this)
        setContentView(R.layout.activity_select_list_for_shopping)

        setupViewModel()
        setupViewState()
        setupToolbar()
        setupRecyclerView()
        setupButtons()

        requestShoppingLists()
    }

    override fun onDestroy() {
        (application as App).releaseSelectListForShoppingComponent()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                returnToPrevious()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        returnToPrevious()
    }

    private fun returnToPrevious() {
        finish()
    }

    private fun onNextClicked() {
        val listNames = adapter.getSelectedLists().map { it.name }
        val intent = Intent(this, ShoppingActivity::class.java)
        intent.putExtra(ShoppingActivity.LISTS_ARG, listNames.toTypedArray())
        startActivity(intent)
    }

    private fun handleViewState(viewState: SelectListForShoppingViewState) {
        adapter.setSelectList(viewState.lists)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(SelectListForShoppingViewModel::class.java)
    }

    private fun setupViewState() {
        viewModel.viewState.observe(this, Observer {
            if(it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer {
            Log.e(TAG, "Error occurred", it)
        })
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.lists_select_screen_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.lists_select_rv)

        adapter = SelectListForShoppingAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dividerDec = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerDec)
    }

    private fun setupButtons() {
        nextBtn = findViewById(R.id.lists_select_next_btn)
        nextBtn.setOnClickListener { onNextClicked() }
    }

    private fun requestShoppingLists() {
        viewModel.getShoppingLists()
    }

    companion object {
        private val TAG = "SelectListForShopping"
    }
}
