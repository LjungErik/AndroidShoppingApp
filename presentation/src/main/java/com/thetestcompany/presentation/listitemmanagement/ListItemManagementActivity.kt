package com.thetestcompany.presentation.listitemmanagement

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.BaseFragment
import com.thetestcompany.presentation.common.CircularRevealSettings
import com.thetestcompany.presentation.entities.ShoppingListItem
import io.reactivex.disposables.CompositeDisposable

class ListItemManagementActivity : AppCompatActivity() {

    private lateinit var listFragment: ListItemManagementListFragment
    private lateinit var framelayout: FrameLayout

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item_management)

        val bundle = intent.extras
        val listName = bundle.getString(ARG_LIST_NAME)
        title = listName

        framelayout = findViewById(R.id.list_item_screen_fragmentcontainer)
        val toolbar = findViewById<Toolbar>(R.id.list_item_screen_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            listFragment = ListItemManagementListFragment.newInstance(listName)

            disposable.add(listFragment.observeItemClicked().subscribe{ onItemClicked(it) })
            disposable.add(listFragment.observeAddClicked().subscribe{ onAddClicked(it) })
            disposable.add(listFragment.observeReturn().subscribe{ onListReturn() })

            transaction.add(R.id.list_item_screen_fragmentcontainer, listFragment, CURR_FRAG_TAG)
            transaction.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                signalBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        signalBackPressed()
    }

    private fun onItemClicked(event: ListItemManagementListFragment.ItemEvent) {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        openChangeFragment(event.shoppingListItem, null)
    }

    private fun onAddClicked(event: ListItemManagementListFragment.AddEvent) {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val duration = resources.getInteger(R.integer.circular_reveal_dur).toLong()
        val revealSettings = CircularRevealSettings(event.centerX, event.centerY, framelayout.width, framelayout.height, duration)
        openChangeFragment(null, revealSettings)
    }

    private fun onListReturn() {
        returnToPrevious()
    }

    private fun onChangeReturn() {
        closeChangeFragment()
    }

    private fun onSave(item: ShoppingListItem) {
        listFragment.saveItem(item)
        closeChangeFragment()
    }

    private fun openChangeFragment(item: ShoppingListItem?, revealSettings: CircularRevealSettings?) {
        val changeFragment = ListItemChangeFragment.newInstance(item, revealSettings)

        disposable.add(changeFragment.observeSave().subscribe { onSave(it) })
        disposable.add(changeFragment.observeReturn().subscribe { onChangeReturn() })

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out_down)
        transaction.replace(R.id.list_item_screen_fragmentcontainer, changeFragment, CURR_FRAG_TAG)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun closeChangeFragment() {
        supportFragmentManager.popBackStack()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun signalBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(CURR_FRAG_TAG)

        if(fragment is BaseFragment) {
            fragment.onBackPressed()
        }
    }

    private fun returnToPrevious() {
        finish()
    }

    companion object {
        private val CURR_FRAG_TAG = "list_fragment"

        val ARG_LIST_NAME = "list_name"
    }
}
