package com.thetestcompany.presentation.listmanagement

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.EditText
import com.thetestcompany.presentation.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class ListManagementCreateDialogFragment: DialogFragment() {

    class CreateListEvent(val name: String)

    private val createSubject: PublishSubject<CreateListEvent> = PublishSubject.create()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater

        builder.setView(inflater.inflate(R.layout.fragment_list_management_dialog, null))
                .setTitle("Ny inköpslista")
                .setPositiveButton("Skapa", {
                    _,_ -> saveList()
                })
                .setNegativeButton("Avbryt", {
                    _,_ -> this.dialog.cancel()
                })

        return builder.create()
    }

    fun observeCreate(): Observable<CreateListEvent> {
        return createSubject
    }

    private fun saveList() {
        val dialogListListName = this.dialog.findViewById<EditText>(R.id.dialog_create_list_name)
        val name = dialogListListName.text.toString()
        createSubject.onNext(CreateListEvent(name))
    }

    companion object {
        fun newInstance(): ListManagementCreateDialogFragment {
            return ListManagementCreateDialogFragment()
        }
    }

}