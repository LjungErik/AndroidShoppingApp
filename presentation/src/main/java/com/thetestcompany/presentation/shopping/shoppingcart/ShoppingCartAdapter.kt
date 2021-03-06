package com.thetestcompany.presentation.shopping.shoppingcart

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.UnitTranslator
import com.thetestcompany.presentation.entities.ShoppingCartItem


class ShoppingCartAdapter(private val ctx: Context, private val unitTranslator: UnitTranslator) : RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartHolder>() {

    private var items: List<ShoppingCartItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShoppingCartHolder {
        val inflater = LayoutInflater.from(ctx)
        val cartEntryView = inflater.inflate(R.layout.shopping_cart_entry, parent, false)
        return ShoppingCartHolder(cartEntryView)
    }

    override fun onBindViewHolder(holder: ShoppingCartHolder?, position: Int) {
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

    fun setItems(cartItems: List<ShoppingCartItem>) {
        items = cartItems
        notifyDataSetChanged()
    }


    class ShoppingCartHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView = itemView.findViewById(R.id.item_name)
        val priceInfoTv: TextView = itemView.findViewById(R.id.item_price_info)
        val totalTv: TextView = itemView.findViewById(R.id.item_total)
    }
}