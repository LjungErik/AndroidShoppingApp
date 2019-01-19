package com.thetestcompany.presentation.receipt.receiptdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.App
import com.thetestcompany.presentation.common.BaseFragment
import com.thetestcompany.presentation.common.UnitTranslator
import javax.inject.Inject


class ReceiptDetailsFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ReceiptDetailsVMFactory

    /* ViewModel for getting receipt details */
    private lateinit var viewModel: ReceiptDetailsViewModel


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReceiptDetailsAdapter

    private lateinit var receiptTotalAmount: TextView

    private var receiptId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as App).createReceiptDetailsComponent().inject(this)

        setupViewModel()
        setupArguments()
        setupViewState()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_receipt_details, container, false)

        setupRecyclerView(rootView)

        setupTotal(rootView)

        requestReceiptDetails()

        return rootView
    }

    override fun onDestroy() {
        (activity.application as App).releaseReceiptDetailsComponent()
        super.onDestroy()
    }

    override fun onBackPressed() {
        signalFinish()
    }

    private fun handleViewState(viewState: ReceiptDetailsViewState) {
        adapter.setReceiptItems(viewState.items)
        receiptTotalAmount.text = "${viewState.total} kr"
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ReceiptDetailsViewModel::class.java)
    }

    private fun setupArguments() {
        if(arguments != null) {
            receiptId = arguments.getLong(ARG_RECEIPT_ID)
        }
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

    private fun setupRecyclerView(rootView: View) {
        val unitTranslator = UnitTranslator(resources)

        recyclerView = rootView.findViewById(R.id.receipt_details_rv)
        adapter = ReceiptDetailsAdapter(activity, unitTranslator)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupTotal(rootView: View) {
        receiptTotalAmount = rootView.findViewById(R.id.receipt_total_amount)
    }

    private fun requestReceiptDetails() {
        viewModel.getReceiptItems(receiptId)
    }

    companion object {
        private val TAG = "ReceiptDetailsFragment"

        private val ARG_RECEIPT_ID = "receipt_id"

        fun newInstance(receiptId: Long): ReceiptDetailsFragment {
            val fragment = ReceiptDetailsFragment()
            val args = Bundle()
            args.putLong(ARG_RECEIPT_ID, receiptId)
            fragment.arguments = args
            return fragment
        }
    }
}