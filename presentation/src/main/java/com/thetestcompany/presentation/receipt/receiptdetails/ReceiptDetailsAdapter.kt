package com.thetestcompany.presentation.receipt.receiptdetails

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.UnitTranslator
import com.thetestcompany.presentation.entities.ReceiptItem
import com.thetestcompany.presentation.entities.ShoppingCartItem


class ReceiptDetailsAdapter(private val ctx: Context, private val unitTranslator: UnitTranslator): RecyclerView.Adapter<ReceiptDetailsAdapter.ReceiptDetailsViewHolder>() {

    private var items: List<ReceiptItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReceiptDetailsViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val cartEntryView = inflater.inflate(R.layout.shopping_cart_entry, parent, false)
        return ReceiptDetailsViewHolder(cartEntryView)
    }

    override fun onBindViewHolder(holder: ReceiptDetailsViewHolder?, position: Int) {
        if(holder != null) {
            val item = items[position]
            holder.nameTv.text = item.name
            holder.priceInfoTv.text = "${item.quantity}${unitTranslator.translate(item.unit)} x ${item.unitPrice}"
            holder.totalTv.text = "${item.totalPrice} kr"
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setReceiptItems(receiptItems: List<ReceiptItem>) {
        items = receiptItems
        notifyDataSetChanged()
    }

    class ReceiptDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView = itemView.findViewById(R.id.item_name)
        val priceInfoTv: TextView = itemView.findViewById(R.id.item_price_info)
        val totalTv: TextView = itemView.findViewById(R.id.item_total)
    }
}