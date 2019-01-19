package com.thetestcompany.presentation.listmanagement

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.thetestcompany.presentation.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class DeleteModeCallback : ActionMode.Callback {

    class DeleteEvent
    class ExitEvent

    private val deleteSubject: PublishSubject<DeleteEvent> = PublishSubject.create()
    private val exitSubject: PublishSubject<ExitEvent> = PublishSubject.create()

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_delete_mode, menu)

        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_delete -> {
                deleteSubject.onNext(DeleteEvent())
                true
            }
            else ->
                false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        exitSubject.onNext(ExitEvent())
    }

    fun observDelete() : Observable<DeleteEvent> {
        return deleteSubject
    }

    fun observExit() : Observable<ExitEvent> {
        return exitSubject
    }

}