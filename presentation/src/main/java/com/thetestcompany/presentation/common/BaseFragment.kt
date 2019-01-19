package com.thetestcompany.presentation.common

import android.support.v4.app.Fragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class BaseFragment : Fragment() {

    private val returnSubject: PublishSubject<Boolean> = PublishSubject.create()

    fun observeReturn(): Observable<Boolean> {
        return returnSubject
    }

    protected fun signalFinish() {
        returnSubject.onNext(true)
    }

    abstract fun onBackPressed()
}