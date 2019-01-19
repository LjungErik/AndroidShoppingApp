package com.thetestcompany.presentation.shopping.shoppingtobuy

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.UnitTranslator
import com.thetestcompany.presentation.entities.ShoppingToBuyItem


class ShoppingToBuyAdapter(private val ctx: Context,
                           private val unitTranslator: UnitTranslator): RecyclerView.Adapter<ShoppingToBuyAdapter.ShoppingToBuyHolder>() {

    private var items: List<ShoppingToBuyItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShoppingToBuyHolder {
        val inflater = LayoutInflater.from(ctx)
        val tobuyEntryView = inflater.inflate(R.layout.shopping_tobuy_entry, parent, false)
        return ShoppingToBuyHolder(tobuyEntryView)
    }

    override fun onBindViewHolder(holder: ShoppingToBuyHolder?, position: Int) {
        if(holder != null) {
            val item = items[position]

            applyItem(holder, item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(toBuyItems: List<ShoppingToBuyItem>) {
        items = toBuyItems
        notifyDataSetChanged()
    }

    private fun applyItem(holder: ShoppingToBuyHolder, item: ShoppingToBuyItem) {
        holder.nameTv.text = item.name
        holder.quantityTv.text = "${item.quantity}${unitTranslator.translate(item.unit)}"

        if(item.boughtQuantity > 0.0) {
            holder.newQuantityTv.text = "${item.quantity-item.boughtQuantity}${unitTranslator.translate(item.unit)}"
            holder.newQuantityTv.visibility = View.VISIBLE
            holder.quantityTv.paintFlags = (holder.quantityTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
        }
        else {
            holder.newQuantityTv.visibility = View.GONE
        }
    }

    class ShoppingToBuyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView = itemView.findViewById(R.id.item_name)
        val quantityTv: TextView = itemView.findViewById(R.id.item_original_qunatity)
        val newQuantityTv: TextView = itemView.findViewById(R.id.item_new_quantity)
    }
}