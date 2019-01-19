package com.thetestcompany.presentation.listitemmanagement

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
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
import com.thetestcompany.presentation.common.UnitTranslator
import com.thetestcompany.presentation.entities.ShoppingListItem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ListItemManagementListFragment : BaseFragment() {

    class ItemEvent(val shoppingListItem: ShoppingListItem)
    class AddEvent(val centerX: Int, val centerY: Int)

    /* Dagger inject vmFactory */
    @Inject
    lateinit var vmFactory: ListItemManagementVMFactory

    /* ViewModel to observe state */
    private lateinit var viewModel: ListItemManagementViewModel

    private lateinit var listName: String

    /* RecyclerView and Adapter */
    private lateinit var recyclerView: RecyclerView
    private lateinit var changeListBtn: FloatingActionButton
    private lateinit var adapter: ListItemManagementListAdapter

    private lateinit var unitTranslator: UnitTranslator

    private var checkboxVisibility: Boolean = false

    /* Publish Subjects for signaling events */
    private val itemClickedSubject: PublishSubject<ItemEvent> = PublishSubject.create()
    private val addClickedSubject: PublishSubject<AddEvent> = PublishSubject.create()

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createListItemManagementComponent().inject(this)

        setupViewModel()
        setupArguments()
        setupViewState()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_list_item_management_list, container, false)

        setupTranslators()
        setupRecyclerView(rootView)
        setupButtons(rootView)

        requestShoppingListItems()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }

    override fun onDestroy() {
        (activity?.application as App).releaseListItemManagementComponent()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if(checkboxVisibility) {
            checkboxVisibility = false
            changeButtonImage(checkboxVisibility)

            adapter.setCheckboxVisibility(checkboxVisibility, true)
            recyclerView.post{ adapter.resetFlags() }
        }
        else {
            signalFinish()
        }
    }

    fun observeItemClicked(): Observable<ItemEvent> {
        return itemClickedSubject
    }

    fun observeAddClicked(): Observable<AddEvent> {
        return addClickedSubject
    }

    fun saveItem(item: ShoppingListItem) {
        item.listName = this.listName
        viewModel.saveShoppingListItem(item)
    }

    private fun handleViewState(viewState: ListItemManagementViewState) {
        adapter.setItems(viewState.items)
        if(viewState.deletedItem != null) {
            showUndo(viewState.deletedItem!!)
        }
    }

    private fun onChangeClicked()
    {
        if(checkboxVisibility) {
            val centerX = (changeListBtn.x + (changeListBtn.width/2)).toInt()
            val centerY = (changeListBtn.y + (changeListBtn.height/2)).toInt()
            val event = AddEvent(centerX, centerY)
            addClickedSubject.onNext(event)
        }
        else {
            checkboxVisibility = true
            changeButtonImage(checkboxVisibility)
            adapter.setCheckboxVisibility(checkboxVisibility, true)

            recyclerView.post{ adapter.resetFlags() }
        }
    }

    private fun onItemDeselected(item: ShoppingListItem) {
        viewModel.removeShoppingListItem(item)
    }

    private fun onItemClicked(item: ShoppingListItem) {
        val event = ItemEvent(item)
        itemClickedSubject.onNext(event)
    }

    private fun showUndo(deletedItem: ShoppingListItem) {
        val snackbar = Snackbar.make(recyclerView, deletedItem.name + " removed from list!", Snackbar.LENGTH_LONG)

        snackbar.setAction("UNDO", { _ ->
            deletedItem.id = 0L
            viewModel.saveShoppingListItem(deletedItem)
        })

        snackbar.setActionTextColor(Color.YELLOW)
        snackbar.show()
    }

    private fun changeButtonImage(addMode: Boolean) {
        if(addMode)
            changeListBtn.setImageResource(R.drawable.ic_add)
        else
            changeListBtn.setImageResource(R.drawable.ic_edit)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ListItemManagementViewModel::class.java)
    }

    private fun setupArguments() {
        if (arguments != null) {
            listName = arguments.getString(ARG_LIST_NAME)
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

    private fun requestShoppingListItems() {
        viewModel.getShoppingListItems(listName)
    }

    private fun setupTranslators() {
        unitTranslator = UnitTranslator(resources)
    }

    private fun setupRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.list_item_list_Rv)

        adapter = ListItemManagementListAdapter(activity, unitTranslator)
        adapter.setCheckboxVisibility(checkboxVisibility, false)

        disposable.add(adapter.observeDeselect().subscribe({ onItemDeselected(it) }))
        disposable.add(adapter.observeItemClicked().subscribe({ onItemClicked(it) }))

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val dividerDec = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerDec)
    }

    private fun setupButtons(rootView: View) {
        changeListBtn = rootView.findViewById(R.id.list_item_list_edit_btn)
        changeListBtn.setOnClickListener { onChangeClicked() }

        changeButtonImage(checkboxVisibility)
    }

    companion object {
        private val TAG = "ItemListFragment"

        private val ARG_LIST_NAME = "list_name"

        fun newInstance(listName: String): ListItemManagementListFragment {
            val fragment = ListItemManagementListFragment()
            val args = Bundle()
            args.putString(ARG_LIST_NAME, listName)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
