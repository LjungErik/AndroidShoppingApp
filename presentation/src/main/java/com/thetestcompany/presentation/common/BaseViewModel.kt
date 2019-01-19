package com.thetestcompany.presentation.common

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposable()
    }

}