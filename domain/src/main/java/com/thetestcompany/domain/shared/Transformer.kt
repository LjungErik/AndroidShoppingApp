package com.thetestcompany.domain.shared

import io.reactivex.ObservableTransformer


abstract class Transformer<T> : ObservableTransformer<T, T>