package com.thetestcompany.presentation.listmanagement

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.view.ActionMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.App
import com.thetestcompany.presentation.entities.ShoppingList
import com.thetestcompany.presentation.listitemmanagement.ListItemManagementActivity
import io.reactivex.disposables.CompositeDisposable
import java.util.Date
import javax.inject.Inject

class ListManagementActivity : AppCompatActivity() {

    /* Dagger inject vmFactory */
    @Inject
    lateinit var vmFactory: ListManagementVMFactory

    /* ViewModel to observe state */
    private lateinit var viewModel: ListManagementViewModel

    /* Views inside activity */
    private lateinit var recyclerView: RecyclerView
    private lateinit var addNewListBtn: Button
    private lateinit var toolbar: Toolbar

    /* Adapter for recyclerview */
    private lateinit var adapter: ListManagementAdapter

    /* statusBarColors */
    private var defaultStatusBarColor: Int? = null
    private var actionModeStatusBarColor: Int? = null

    /* Delete mode callback */
    private lateinit var deleteModeCallback: DeleteModeCallback
    private var deleteMode: ActionMode? = null

    /* Disposable to unsubscribe */
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).createListManagementComponent().inject(this)

        setContentView(R.layout.activity_list_management)

        setupToolbar()
        saveStatusBarColors()
        setupRecyclerView()
        setupButtons()
        setupViewModel()
        setupViewState()
        setupActionModeCallback()

        requestShoppingLists()
    }

    override fun onDestroy() {
        disposable.clear()
        (application as App).releaseListManagementComponent()
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

    private fun onCreateNewListClicked() {
        val dialog = ListManagementCreateDialogFragment.newInstance()
        disposable.add(dialog.observeCreate().subscribe{ onNewListCreated(it)})
        dialog.show(fragmentManager, null)
    }

    private fun onNewListCreated(event: ListManagementCreateDialogFragment.CreateListEvent) {
        val shoppingList = ShoppingList(0, event.name, Date(), getRandomColor())
        viewModel.addShoppingList(shoppingList)
    }

    private fun onCallbackDelete() {
        adapter.resetAnimationIndex()
        val listsToDelete = adapter.getSelectedLists()
        deleteMode?.finish()
        viewModel.removeShoppingLists(listsToDelete.map { it.value })
    }

    private fun onCallbackExit() {
        adapter.clearSelections()
        deleteMode = null
        setStatusBarColor(defaultStatusBarColor)
        recyclerView.post {
            adapter.resetAnimationIndex()
        }
    }

    private fun onSelectionChange(selectedCount: Int) {
        if(deleteMode == null) {
            deleteMode = startSupportActionMode(deleteModeCallback)
            setStatusBarColor(actionModeStatusBarColor)
        }

        if(selectedCount == 0) {
            deleteMode?.finish()
        }
        else {
            deleteMode?.title = selectedCount.toString()
            deleteMode?.invalidate()
        }
    }

    private fun onListClicked(list: ShoppingList) {
        val intent = Intent(this, ListItemManagementActivity::class.java)
        val bundle = Bundle()
        bundle.putString(ListItemManagementActivity.ARG_LIST_NAME, list.name)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun handleViewState(viewState: ListManagementViewState) {
        adapter.setLists(viewState.lists)
        if(viewState.deletedLists != null)
        {
            showUndo(viewState.deletedLists!!)
        }
    }

    private fun getRandomColor() : Int {
        val colors = resources.obtainTypedArray(R.array.mdcolor)
        val index: Int = (Math.random() * colors.length()).toInt()

        val ret = colors.getColor(index, Color.GRAY)
        colors.recycle()

        return ret
    }

    private fun showUndo(deletedLists: List<ShoppingList>) {
        val snackbar = Snackbar.make(recyclerView, deletedLists.size.toString() + " deleted", Snackbar.LENGTH_LONG)

        snackbar.setAction("UNDO", { _ ->
            val lists = deletedLists.map {
                it.id = 0L
                it
            }
            viewModel.addShoppingLists(lists)
        })

        snackbar.setActionTextColor(Color.YELLOW)
        snackbar.show()
    }

    private fun setStatusBarColor(color: Int?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && color != null) {
            window.statusBarColor = color
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.list_management_screen_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun saveStatusBarColors() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            defaultStatusBarColor = window.statusBarColor
            actionModeStatusBarColor = ResourcesCompat.getColor(resources, R.color.colorActionModeStatusBar, null)
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.list_management_rv)

        adapter = ListManagementAdapter(this)
        disposable.add(adapter.observeListClicked().subscribe { onListClicked(it) })
        disposable.add(adapter.observeSelection().subscribe { onSelectionChange(it) })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dividerDec = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerDec)
    }

    private fun setupButtons() {
        addNewListBtn = findViewById(R.id.add_new_list_btn)
        addNewListBtn.setOnClickListener { onCreateNewListClicked() }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ListManagementViewModel::class.java)
    }

    private fun setupViewState() {
        viewModel.viewState.observe(this, Observer {
            if(it != null) handleViewState(it)
        })
        viewModel.errorState.observe(this, Observer {
            Log.e(TAG, "Error occurred", it)
        })
    }

    private fun setupActionModeCallback() {
        deleteModeCallback = DeleteModeCallback()

        disposable.add(deleteModeCallback.observDelete().subscribe{ onCallbackDelete() })
        disposable.add(deleteModeCallback.observExit().subscribe { onCallbackExit() })
    }

    private fun requestShoppingLists() {
        viewModel.getShoppingLists()
    }

    companion object {
        private val TAG: String = "ListManagementActivity"
    }
}
