package com.thetestcompany.data.networkservice

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StoreApi {

    @POST("products/lookup")
    fun lookupBarcode(@Body request: LookupRequest): Observable<ProductResult>
}