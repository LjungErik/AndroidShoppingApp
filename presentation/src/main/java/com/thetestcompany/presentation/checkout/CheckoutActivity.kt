package com.thetestcompany.presentation.checkout

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.App
import com.thetestcompany.presentation.common.UnitTranslator
import com.thetestcompany.presentation.receipt.ReceiptActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CheckoutActivity : AppCompatActivity() {

    /* ViewModel factory */
    @Inject
    lateinit var vmFactory: CheckoutVMFactory

    /* ViewModel */
    private lateinit var viewModel: CheckoutViewModel

    private lateinit var toolbar: Toolbar

    /* Adapter for recycler view */
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheckoutCartAdapter

    private lateinit var payButton: Button

    private lateinit var totalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).createCheckoutComponent().inject(this)
        setContentView(R.layout.activity_checkout)

        setupViewModel()

        setupViewState()

        setupToolbar()

        setupRecyclerView()

        setupTotalView()

        setupButtons()

        requestItemsInCart()
    }

    override fun onDestroy() {
        (application as App).releaseCheckoutComponent()
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

    private fun onPayClicked() {
        viewModel.createReceipt(SimpleDateFormat("yyyy-MM-dd").format(Date()), "Stora marknaden")
    }

    private fun handleViewState(viewState: CheckoutViewState) {
        if(viewState.insertedReceiptId != null) {
            showReceipt(viewState.insertedReceiptId)
            return
        }

        totalAmount.text = "${viewState.total} kr"

        if(viewState.items.any() && !payButton.isEnabled) {
            payButton.isEnabled = true
            payButton.setBackgroundResource(R.drawable.blue_button)
        }

        adapter.setItems(viewState.items)
    }

    private fun showReceipt(receiptId: Long) {
        /* Start Receipt activity */
        val intent = Intent(this, ReceiptActivity::class.java).apply {
            putExtra(ReceiptActivity.ARG_RECEIPT_ID, receiptId)
        }
        startActivity(intent)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(CheckoutViewModel::class.java)
    }

    private fun setupViewState() {
        viewModel.viewState.observe(this, Observer {
            if(it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer {
            //handle ErrorState updates
            Log.e(CheckoutActivity.TAG, "Unable to perform action", it)
        })
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.checkout_screen_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Checkout"
    }

    private fun setupRecyclerView() {
        val unitTranslator = UnitTranslator(resources)

        recyclerView = findViewById(R.id.checkout_cart_items_rv)
        adapter = CheckoutCartAdapter(this, unitTranslator)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupTotalView() {
        totalAmount = findViewById(R.id.checkout_total_amount)
    }

    private fun setupButtons() {
        payButton = findViewById(R.id.checkout_pay_btn)
        payButton.isEnabled = false
        payButton.setBackgroundResource(R.drawable.disabled_button)

        payButton.setOnClickListener {
            onPayClicked()
        }
    }

    private fun requestItemsInCart() {
        viewModel.getItemsInCart()
    }

    companion object {
        private val TAG = "CheckoutActivty"
    }
}
