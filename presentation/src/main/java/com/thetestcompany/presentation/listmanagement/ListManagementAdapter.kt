package com.thetestcompany.presentation.listmanagement

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.entities.ShoppingList
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.text.SimpleDateFormat


class ListManagementAdapter(private val ctx: Context): RecyclerView.Adapter<ListManagementAdapter.ListManagementHolder>() {

    private val selectedItemsIndex: SparseBooleanArray = SparseBooleanArray()
    private val animateItemsIndex: SparseBooleanArray = SparseBooleanArray()

    private var reverseAnimation: Boolean = false
    private var currentSelectedIndex: Int = -1

    private var lists: List<ShoppingList> = listOf()

    private val selectionSubject: PublishSubject<Int> = PublishSubject.create()
    private val listClickedSubject: PublishSubject<ShoppingList> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListManagementHolder {
        val inflater = LayoutInflater.from(ctx)
        val listManagementEntry = inflater.inflate(R.layout.listmanagement_entry, parent, false)
        return ListManagementHolder(listManagementEntry)
    }

    override fun onBindViewHolder(holder: ListManagementHolder?, position: Int) {
        if(holder != null) {
            val list = lists[position]

            applyList(holder, list)
            applyIconSelect(holder, position)
            applyIconProfile(holder, list)
            applyBgColor(holder, position)
            applyListeners(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun setLists(shoppingLists: List<ShoppingList>) {
        lists = shoppingLists
        notifyDataSetChanged()
    }

    fun observeSelection(): Observable<Int> {
        return selectionSubject
    }

    fun observeListClicked(): Observable<ShoppingList> {
        return listClickedSubject
    }

    fun resetAnimationIndex() {
        reverseAnimation = false
        animateItemsIndex.clear()
        resetCurrentIndex()
    }

    fun clearSelections() {
        reverseAnimation = true
        selectedItemsIndex.clear()
        notifyDataSetChanged()
    }

    fun getSelectedLists() : Map<Int, ShoppingList> {
        val selected = HashMap<Int, ShoppingList>()
        var i = 0
        while(i < selectedItemsIndex.size()) {
            val index = selectedItemsIndex.keyAt(i)
            selected[index] = lists[index]
            i++
        }
        return selected
    }

    private fun toggleSelect(position: Int) {
        currentSelectedIndex = position
        if(selectedItemsIndex.get(position, false)) {
            selectedItemsIndex.delete(position)
            animateItemsIndex.delete(position)
        }
        else {
            selectedItemsIndex.append(position, true)
            animateItemsIndex.append(position, true)
        }
        selectionSubject.onNext(selectedItemsIndex.size())
        notifyItemChanged(position)
    }

    private fun listClicked(position: Int) {
        listClickedSubject.onNext(lists[position])
    }

    private fun applyList(holder: ListManagementHolder, list: ShoppingList) {
        holder.listName.text = list.name
        holder.listDate.text = SimpleDateFormat("MMM d").format(list.createdAt)
    }

    private fun applyIconSelect(holder: ListManagementHolder, index: Int) {
        if(selectedItemsIndex.get(index, false)) {
            holder.iconBack.rotationY = 0.0f
            holder.iconBack.alpha = 1.0f
            holder.iconFront.alpha = 0.0f
            if(currentSelectedIndex == index) {
                animateSelect(holder)
                resetCurrentIndex()
            }
        }
        else {
            holder.iconFront.rotationY = 0.0f
            holder.iconFront.alpha = 1.0f
            holder.iconBack.alpha = 0.0f
            if((reverseAnimation && animateItemsIndex.get(index, false)) || currentSelectedIndex == index) {
                animateDeselect(holder)
                resetCurrentIndex()
            }
        }
    }

    private fun applyIconProfile(holder: ListManagementHolder, list: ShoppingList) {
        holder.iconProfile.setImageResource(R.drawable.bg_circle)
        holder.iconProfile.setColorFilter(list.color)
        holder.iconText.text = list.name.substring(0,1).toUpperCase()
    }

    private fun applyBgColor(holder: ListManagementHolder, index: Int) {
        if(selectedItemsIndex.get(index, false)) {
            val color = ResourcesCompat.getColor(ctx.resources, R.color.colorSelectedList, null)
            holder.containerView.setBackgroundColor(color)
        }
        else {
            val color = ResourcesCompat.getColor(ctx.resources, R.color.colorUnselectedList, null)
            holder.containerView.setBackgroundColor(color)
        }
    }

    private fun applyListeners(holder: ListManagementHolder, index: Int) {
        holder.textContainer.setOnClickListener { _ ->
            listClicked(index)
        }

        holder.iconContainer.setOnClickListener { _ ->
            toggleSelect(index)
        }

        holder.textContainer.setOnLongClickListener { v ->
            toggleSelect(index)
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            true
        }
    }

    private fun animateSelect(holder: ListManagementHolder) {
        val flipInLeft = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_left_in)
        val flipOutRight = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_right_out)

        flipInLeft.setTarget(holder.iconBack)
        flipOutRight.setTarget(holder.iconFront)

        val showBack = AnimatorSet()
        showBack.playTogether(flipInLeft, flipOutRight)
        showBack.start()
    }

    private fun animateDeselect(holder: ListManagementHolder) {
        val flipOutLeft = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_right_out)
        val flipInRight = AnimatorInflater.loadAnimator(ctx, R.animator.card_flip_left_in)

        flipInRight.setTarget(holder.iconFront)
        flipOutLeft.setTarget(holder.iconBack)

        val showFront = AnimatorSet()
        showFront.playTogether(flipInRight, flipOutLeft)
        showFront.start()
    }

    private fun resetCurrentIndex() {currentSelectedIndex = -1}

    class ListManagementHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listName: TextView = itemView.findViewById(R.id.list_name)
        val listDate: TextView = itemView.findViewById(R.id.list_date)
        val iconBack: View = itemView.findViewById(R.id.icon_back)
        val iconFront: View = itemView.findViewById(R.id.icon_front)
        val iconProfile: ImageView = itemView.findViewById(R.id.icon_profile)
        val iconText: TextView = itemView.findViewById(R.id.icon_text)
        val containerView: View = itemView.findViewById(R.id.list_fg)
        val iconContainer: View = itemView.findViewById(R.id.icon_container)
        val textContainer: View = itemView.findViewById(R.id.text_container)
    }
}