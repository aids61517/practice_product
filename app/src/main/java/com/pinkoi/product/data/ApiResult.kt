package com.pinkoi.product.data

import com.google.gson.annotations.SerializedName

data class ApiResult(
  @SerializedName("result") val result: List<Product>
)