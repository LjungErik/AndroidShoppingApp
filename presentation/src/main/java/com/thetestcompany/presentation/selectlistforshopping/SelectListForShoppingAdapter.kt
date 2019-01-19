package com.thetestcompany.presentation.selectlistforshopping

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.entities.ShoppingList
import java.text.SimpleDateFormat


class SelectListForShoppingAdapter(private val ctx: Context) : RecyclerView.Adapter<SelectListForShoppingAdapter.SelectListForShoppingHolder>() {

    private val selectedItemsIndex: SparseBooleanArray = SparseBooleanArray()
    private var lists: List<ShoppingList> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectListForShoppingHolder {
        val inflater = LayoutInflater.from(ctx)
        val shoppingListSelect = inflater.inflate(R.layout.shopping_list_select, parent, false)
        return SelectListForShoppingHolder(shoppingListSelect)
    }

    override fun onBindViewHolder(holder: SelectListForShoppingHolder?, position: Int) {
        if(holder != null) {
            val list = lists[position]
            applyList(holder, list)
            applyListSelect(holder, position)
            applyListeners(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun getSelectedLists() : List<ShoppingList> {
        val selected: MutableList<ShoppingList> = mutableListOf()
        var i = 0
        while(i < selectedItemsIndex.size()) {
            var index = selectedItemsIndex.keyAt(i)
            selected.add(lists[index])
            i++
        }
        return selected
    }

    fun setSelectList(shoppingLists: List<ShoppingList>) {
        lists = shoppingLists
        notifyDataSetChanged()
    }

    private fun onChecked(position: Int, isChecked: Boolean) {
        if(isChecked) {
            selectedItemsIndex.append(position, isChecked)
        }
        else {
            selectedItemsIndex.delete(position)
        }
    }

    private fun applyList(holder: SelectListForShoppingHolder, list: ShoppingList) {
        holder.nameTv.text = list.name
        holder.dateTv.text = SimpleDateFormat("MMM d").format(list.createdAt)
    }

    private fun applyListSelect(holder: SelectListForShoppingHolder, position: Int) {
        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = selectedItemsIndex.get(position, false)
    }

    private fun applyListeners(holder: SelectListForShoppingHolder, position: Int) {
        holder.checkbox.setOnCheckedChangeListener { _, b -> onChecked(position, b) }
    }

    class SelectListForShoppingHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val checkbox: CheckBox = itemView.findViewById(R.id.list_select_check)
        val nameTv: TextView = itemView.findViewById(R.id.list_name)
        val dateTv: TextView = itemView.findViewById(R.id.list_date)
    }
}