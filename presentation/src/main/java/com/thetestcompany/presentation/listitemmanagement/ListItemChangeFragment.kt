package com.thetestcompany.presentation.listitemmanagement

import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.thetestcompany.domain.entities.UnitsOfQuantity
import com.thetestcompany.presentation.R
import com.thetestcompany.presentation.common.AnimationUtils
import com.thetestcompany.presentation.common.BaseFragment
import com.thetestcompany.presentation.common.CircularRevealSettings
import com.thetestcompany.presentation.entities.ShoppingListItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ListItemChangeFragment: BaseFragment() {

    private lateinit var fragmentContainer: CoordinatorLayout
    private lateinit var nameEt: AppCompatEditText
    private lateinit var quantityEt: AppCompatEditText
    private lateinit var unitSp: Spinner
    private lateinit var saveBtn: FloatingActionButton
    private var revealSettings: CircularRevealSettings? = null

    /* hold the unique param_id of the entry */
    private var param_id: Long? = null
    private var param_name: String? = null
    private var param_quantity: Double? = null
    private var param_unit_id: Int? = null

    private val saveSubject: PublishSubject<ShoppingListItem> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            revealSettings = arguments.getParcelable(ARG_REVEAL_SETTINGS)

            param_id = arguments.getLong(ARG_ID)
            param_name = arguments.getString(ARG_NAME)
            param_quantity = arguments.getDouble(ARG_QUANTITY)
            param_unit_id = arguments.getInt(ARG_UNIT_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val rootView = inflater!!.inflate(R.layout.fragment_list_item_change, container, false)

        fragmentContainer = rootView.findViewById(R.id.item_container)

        nameEt = rootView.findViewById(R.id.item_name_ev)
        nameEt.requestFocus()

        quantityEt = rootView.findViewById(R.id.item_quantity_ev)
        unitSp = rootView.findViewById(R.id.item_unit_sp)

        val units = resources.getStringArray(R.array.units)
        val adapter = ArrayAdapter(context, R.layout.item_change_spinner, units)
        unitSp.adapter = adapter

        saveBtn = rootView.findViewById(R.id.item_save_btn)
        saveBtn.setOnClickListener { onSaveClicked() }

        populateFields()

        if(revealSettings != null)
            AnimationUtils.registerCircularAnimationReveal(rootView, revealSettings as CircularRevealSettings)

        return rootView
    }

    override fun onBackPressed() {
        signalFinish()
    }

    fun observeSave(): Observable<ShoppingListItem> {
        return saveSubject
    }

    private fun onSaveClicked() {

        if(isValidInput()) {
            val name = nameEt.text.toString()
            val quantity = quantityEt.text.toString().toDouble()
            val unitId = unitSp.selectedItemPosition

            val item = ShoppingListItem(0, name, quantity, UnitsOfQuantity.fromInt(unitId))

            if(param_id != null) {
                item.id = this.param_id as Long
            }

            hideKeyboard()
            saveSubject.onNext(item)
        }
    }

    private fun isValidInput() : Boolean {
        val name = nameEt.text.toString()
        val quantity = quantityEt.text.toString()

        if(name.isEmpty()) {
            nameEt.error = "Namn krävs"
            return false
        }

        if(quantity.isEmpty()) {
            quantityEt.error = "Mängd krävs"
            return false
        }

        if(quantity.toDoubleOrNull() == null) {
            quantityEt.error = "Ogiltigt nummer"
            return false
        }

        return true
    }

    private fun hideKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(fragmentContainer.windowToken, 0)
    }

    private fun populateFields() {
        if(param_name != null) {
            nameEt.setText(param_name, TextView.BufferType.EDITABLE)
        }

        if(param_quantity != null) {
            quantityEt.setText(param_quantity.toString(), TextView.BufferType.EDITABLE)
        }

        if(param_unit_id != null) {
            unitSp.setSelection(param_unit_id as Int)
        }
    }

    companion object {

        /* Bundle names for arguments */
        private val ARG_ID = "entry_id"
        private val ARG_NAME = "entry_name"
        private val ARG_QUANTITY = "entry_quantity"
        private val ARG_UNIT_ID = "entry_unitid"
        private val ARG_REVEAL_SETTINGS = "reveal_settings"

        fun newInstance(item: ShoppingListItem?, settings: CircularRevealSettings? = null): ListItemChangeFragment {
            val fragment = ListItemChangeFragment()
            val args = Bundle()
            if(item != null) {
                args.putLong(ARG_ID, item.id)
                args.putString(ARG_NAME, item.name)
                args.putDouble(ARG_QUANTITY, item.quantity)
                args.putInt(ARG_UNIT_ID, item.unit.id)
            }

            if(settings != null) {
                args.putParcelable(ARG_REVEAL_SETTINGS, settings)
            }
            fragment.arguments = args
            return fragment
        }
    }
}