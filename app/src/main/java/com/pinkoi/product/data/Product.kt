package com.pinkoi.product.data

import java.io.Serializable

class Product (
  val name: String,
  val price: Int,
  val imageId: Int
): Serializable