package com.thetestcompany.presentation.shopping

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.EditText
import com.thetestcompany.presentation.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ShoppingAbortDialogFragment : DialogFragment() {

    class AbortEvent()

    private val abortSubject: PublishSubject<AbortEvent> = PublishSubject.create()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater

        builder.setView(inflater.inflate(R.layout.fragment_shopping_abort_dialog, null))
                .setTitle("Avbryt shoppingen")
                .setPositiveButton("Ja", {
                    _,_ -> abortShopping()
                })
                .setNegativeButton("Nej", {
                    _,_ -> this.dialog.cancel()
                })

        return builder.create()
    }

    fun observeCreate(): Observable<AbortEvent> {
        return abortSubject
    }

    private fun abortShopping() {
        abortSubject.onNext(AbortEvent())
    }

    companion object {
        fun newInstance(): ShoppingAbortDialogFragment {
            return ShoppingAbortDialogFragment()
        }
    }
}