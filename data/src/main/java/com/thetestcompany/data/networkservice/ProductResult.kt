package com.thetestcompany.data.networkservice

import com.google.gson.annotations.SerializedName
import com.thetestcompany.data.entities.ProductData
import com.thetestcompany.data.entities.ShoppingCartItemData

class ProductResult {

    @SerializedName("product")
    var product: ProductData? = null
}