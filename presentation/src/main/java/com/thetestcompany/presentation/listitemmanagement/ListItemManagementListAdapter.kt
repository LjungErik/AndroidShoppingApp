package com.thetestcompany.presentation.listitemmanagement

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.UnitTranslator
import com.thetestcompany.presentation.entities.ShoppingListItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ListItemManagementListAdapter(private val ctx: Context, private val unitTranslator: UnitTranslator): RecyclerView.Adapter<ListItemManagementListAdapter.ListItemManagementListHolder>() {

    private var isCheckboxVisible: Boolean = false
    private var hasVisibilityChanged: Boolean = false

    private val checkboxMaxWidth = ctx.resources.getDimension(R.dimen.checkbox_max_width).toInt()
    private val slideDuration = ctx.resources.getInteger(R.integer.cb_slide_anim_dur).toLong()
    private val fadeDuration = ctx.resources.getInteger(R.integer.cb_fade_anim_dur).toLong()

    private var items: List<ShoppingListItem> = listOf()

    private val deselectSubject: PublishSubject<ShoppingListItem> = PublishSubject.create()
    private val itemClickedSubject: PublishSubject<ShoppingListItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemManagementListHolder {
        val inflater = LayoutInflater.from(ctx)
        val listItemManagementEntry = inflater.inflate(R.layout.listitemmanagement_entry, parent, false)
        return ListItemManagementListHolder(listItemManagementEntry)
    }

    override fun onBindViewHolder(holder: ListItemManagementListHolder?, position: Int) {
        if(holder != null) {
            val item = items[position]

            applyItem(holder, item)
            applyItemSelect(holder)
            applyItemAnimation(holder)
            applyClickListeners(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun observeDeselect(): Observable<ShoppingListItem> {
        return deselectSubject
    }

    fun observeItemClicked(): Observable<ShoppingListItem> {
        return itemClickedSubject
    }

    fun setItems(newItems: List<ShoppingListItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun resetFlags() {
        hasVisibilityChanged = false
    }

    fun setCheckboxVisibility(isVisible: Boolean, animateChange: Boolean) {
        hasVisibilityChanged = animateChange
        isCheckboxVisible = isVisible
        notifyDataSetChanged()
    }

    private fun onCheckboxChange(isChecked: Boolean, index: Int) {
        if(!isChecked) {
            deselectSubject.onNext(items[index])
        }
    }

    private fun onItemClicked(index: Int) {
        itemClickedSubject.onNext(items[index])
    }

    private fun applyItem(holder: ListItemManagementListHolder, item: ShoppingListItem) {
        val amount = "${item.quantity} ${unitTranslator.translate(item.unit)}"
        holder.itemName.text = item.name
        holder.itemAmount.text = amount
    }

    private fun applyItemSelect(holder: ListItemManagementListHolder) {
        holder.itemCheck.setOnCheckedChangeListener(null)
        holder.itemCheck.isChecked = true
    }

    private fun applyItemAnimation(holder: ListItemManagementListHolder) {
        if (isCheckboxVisible) {
            holder.itemCheck.alpha = 1.0f
            val params = holder.itemCheck.layoutParams
            params.width = checkboxMaxWidth

            if (hasVisibilityChanged) {
                animateSlideIn(holder)
            }
        } else {
            holder.itemCheck.alpha = 0.0f
            val params = holder.itemCheck.layoutParams
            params.width = 0

            if (hasVisibilityChanged) {
                animateSlideOut(holder)
            }
        }
    }

    private fun applyClickListeners(holder: ListItemManagementListHolder, index: Int) {
        holder.itemCheck.setOnCheckedChangeListener{ _,b ->
            onCheckboxChange(b, index)
        }
        holder.itemFg.setOnClickListener {
            onItemClicked(index)
        }
    }

    private fun animateSlideIn(holder: ListItemManagementListHolder) {
        holder.itemCheck.alpha = 0.0f
        val params = holder.itemCheck.layoutParams
        params.width = 0

        val slideAnimator = createSlideAnimator(holder.itemCheck, 0, checkboxMaxWidth)
        slideAnimator.duration = slideDuration

        val fadeAnimator = ObjectAnimator.ofFloat(holder.itemCheck, View.ALPHA, 0.0f, 1.0f)
        fadeAnimator.duration = fadeDuration
        fadeAnimator.startDelay = slideDuration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(slideAnimator, fadeAnimator)
        animatorSet.start()
    }

    private fun animateSlideOut(holder: ListItemManagementListHolder) {
        holder.itemCheck.alpha = 1.0f
        val params = holder.itemCheck.layoutParams
        params.width = checkboxMaxWidth

        val slideAnimator = createSlideAnimator(holder.itemCheck, checkboxMaxWidth, 0)
        slideAnimator.duration = slideDuration
        slideAnimator.startDelay = fadeDuration

        val fadeAnimator = ObjectAnimator.ofFloat(holder.itemCheck, View.ALPHA, 1.0f, 0.0f)
        fadeAnimator.duration = fadeDuration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeAnimator, slideAnimator)
        animatorSet.start()
    }

    private fun createSlideAnimator(view: View, fromWidth: Int, toWidth: Int) : ValueAnimator {
        val slideAnimator = ValueAnimator.ofInt(fromWidth, toWidth)
        slideAnimator.setTarget(view)
        slideAnimator.addUpdateListener { a ->
            val params = view.layoutParams
            params.width = a.animatedValue as Int
            view.requestLayout()
        }
        return slideAnimator
    }

    class ListItemManagementListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemName = itemView.findViewById<TextView>(R.id.list_item_entry_name)
        val itemAmount = itemView.findViewById<TextView>(R.id.list_item_entry_amount)
        val itemCheck = itemView.findViewById<CheckBox>(R.id.list_item_check)
        val itemFg = itemView.findViewById<View>(R.id.list_item_fg)
    }
}