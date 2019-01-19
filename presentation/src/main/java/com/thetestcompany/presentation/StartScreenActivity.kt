package com.thetestcompany.presentation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.thetestcompany.presentation.listmanagement.ListManagementActivity
import com.thetestcompany.presentation.receipt.ReceiptActivity
import com.thetestcompany.presentation.selectlistforshopping.SelectListForShoppingActivity

class StartScreenActivity : AppCompatActivity() {

    private lateinit var shopBtn: Button
    private lateinit var listsBtn: Button
    private lateinit var receiptIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        shopBtn = findViewById(R.id.shopBtn)
        shopBtn.setOnClickListener { onShopClicked() }

        listsBtn = findViewById(R.id.listsBtn)
        listsBtn.setOnClickListener { onListsClicked() }

        receiptIcon = findViewById(R.id.receiptIcon)
        receiptIcon.setOnClickListener { onReceiptClicked() }
    }

    private fun onShopClicked() {
        val intent = Intent(this, SelectListForShoppingActivity::class.java)
        startActivity(intent)
    }

    private fun onListsClicked() {
        val intent = Intent(this, ListManagementActivity::class.java)
        startActivity(intent)
    }

    private fun onReceiptClicked() {
       val intent = Intent(this, ReceiptActivity::class.java)
        startActivity(intent)
    }
}
