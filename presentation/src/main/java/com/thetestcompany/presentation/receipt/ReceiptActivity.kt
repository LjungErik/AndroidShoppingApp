package com.thetestcompany.presentation.receipt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.StartScreenActivity
import com.thetestcompany.presentation.common.BaseFragment
import com.thetestcompany.presentation.receipt.receiptdetails.ReceiptDetailsFragment
import com.thetestcompany.presentation.receipt.receiptlist.ReceiptListFragment
import io.reactivex.disposables.CompositeDisposable

class ReceiptActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var fragmentContainer: FrameLayout

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        fragmentContainer = findViewById(R.id.receipt_screen_fragment_container)
        toolbar = findViewById(R.id.receipt_screen_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        val receiptId: Long? = extras?.getLong(ReceiptActivity.ARG_RECEIPT_ID)

        if(savedInstanceState == null) {

            if(receiptId != null) {
                showReceiptDetails(receiptId)
            }
            else {
                showReceiptList()
            }

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

    private fun signalBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(CURR_FRAG_TAG)

        if(fragment is BaseFragment) {
            fragment.onBackPressed()
        }
    }

    private fun showReceiptList() {
        val transaction = supportFragmentManager.beginTransaction()
        val listFragment = ReceiptListFragment.newInstance()

        disposable.add(listFragment.observeReturn().subscribe{ onListReturn() })
        disposable.add(listFragment.observeListClicked().subscribe{ onListClicked(it) })

        transaction.replace(R.id.receipt_screen_fragment_container, listFragment, CURR_FRAG_TAG)
        transaction.commit()
    }

    private fun showReceiptDetails(receiptId: Long) {
        val transaction = supportFragmentManager.beginTransaction()
        val detailsFragment = ReceiptDetailsFragment.newInstance(receiptId)

        disposable.add(detailsFragment.observeReturn().subscribe{ onDetailsReturn() })

        transaction.replace(R.id.receipt_screen_fragment_container, detailsFragment, CURR_FRAG_TAG)
        transaction.commit()
    }

    private fun onListClicked(event: ReceiptListFragment.ListClickEvent) {
        showReceiptDetails(event.receiptId)
    }

    private fun onListReturn() {
        val intent = Intent(this, StartScreenActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }

    private fun onDetailsReturn() {
        showReceiptList()
    }

    companion object {
        private val CURR_FRAG_TAG = "curr_fragment"

        val ARG_RECEIPT_ID = "receipt_id"
    }
}
