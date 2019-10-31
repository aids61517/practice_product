package com.pinkoi.product.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Product (
  @SerializedName("name") val name: String,
  @SerializedName("price") val price: Int,
  val imageId: Int,
  @SerializedName("image_url") val imageUrl: String = ""
): Serializable