package com.thetestcompany.presentation.receipt.receiptlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.App
import com.thetestcompany.presentation.common.BaseFragment
import com.thetestcompany.presentation.entities.Receipt
import com.thetestcompany.presentation.receipt.receiptdetails.ReceiptDetailsFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class ReceiptListFragment: BaseFragment() {

    class ListClickEvent(val receiptId: Long)

    @Inject
    lateinit var vmFactory: ReceiptListVMFactory

    /* ViewModel for getting receipts */
    private lateinit var viewModel: ReceiptListViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReceiptListAdapter

    private val listClicked: PublishSubject<ListClickEvent> = PublishSubject.create()

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity.application as App).createReceiptListComponent().inject(this)

        setupViewModel()
        setupViewState()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_receipt_list, container, false)

        setupRecyclerView(rootView)

        requestReceipts()

        return rootView
    }

    override fun onDestroy() {
        (activity.application as App).releaseReceiptListCompoent()
        disposables.clear()

        super.onDestroy()
    }

    override fun onBackPressed() {
        signalFinish()
    }

    fun observeListClicked(): Observable<ListClickEvent> {
        return listClicked
    }

    private fun onListClicked(receipt: Receipt) {
        listClicked.onNext(ListClickEvent(receipt.id))
    }

    private fun handleViewState(viewState: ReceiptListViewState) {
        adapter.setReceipts(viewState.receipts)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ReceiptListViewModel::class.java)
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
        recyclerView = rootView.findViewById(R.id.receipt_list_rv)
        adapter = ReceiptListAdapter(activity)

        disposables.add(adapter.observeListClicked()
                .subscribe{ onListClicked(it) })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val dividerDec = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerDec)
    }

    private fun requestReceipts() {
        viewModel.getReceipts()
    }

    companion object {
        private val TAG = "ReceiptListFragment"

        fun newInstance(): ReceiptListFragment {
            val fragment = ReceiptListFragment()
            return fragment
        }
    }

}