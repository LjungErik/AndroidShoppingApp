package com.thetestcompany.domain.usecases

import com.thetestcompany.domain.shared.Transformer
import io.reactivex.Observable


abstract class UseCase<T>(private val transformer: Transformer<T>) {

    protected abstract fun createObservable(data: Map<String, Any>?) : Observable<T>

    fun observable(data: Map<String, Any>? = null) : Observable<T> {
        return createObservable(data).compose(transformer)
    }
}