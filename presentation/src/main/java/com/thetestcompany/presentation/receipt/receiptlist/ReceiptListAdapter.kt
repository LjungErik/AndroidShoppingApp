package com.thetestcompany.presentation.receipt.receiptlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.entities.Receipt
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ReceiptListAdapter(private val ctx: Context): RecyclerView.Adapter<ReceiptListAdapter.ReceiptListViewHolder>() {

    private var lists: List<Receipt> = listOf()

    private val listClickedSubject: PublishSubject<Receipt> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReceiptListViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val receiptView = inflater.inflate(R.layout.receipt_list_entry, parent, false)
        return ReceiptListViewHolder(receiptView)
    }

    override fun onBindViewHolder(holder: ReceiptListViewHolder?, position: Int) {
        if(holder != null) {
            val list = lists[position]

            setupHolder(holder, list)
            setupListener(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun setReceipts(receipts: List<Receipt>) {
        lists = receipts
        notifyDataSetChanged()
    }

    fun observeListClicked(): Observable<Receipt> {
        return listClickedSubject
    }

    private fun onListClicked(index: Int) {
        listClickedSubject.onNext(lists[index])
    }

    private fun setupHolder(holder: ReceiptListViewHolder, list: Receipt) {
        holder.receiptName.text = list.name
        holder.receiptLocation.text = list.location
    }

    private fun setupListener(holder: ReceiptListViewHolder, index: Int) {
        holder.receiptContainer.setOnClickListener {
            onListClicked(index)
        }
    }

    class ReceiptListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receiptContainer: View = itemView.findViewById(R.id.receipt_list_entry_container)
        val receiptName: TextView = itemView.findViewById(R.id.receipt_name)
        val receiptLocation: TextView = itemView.findViewById(R.id.receipt_location)
    }
}