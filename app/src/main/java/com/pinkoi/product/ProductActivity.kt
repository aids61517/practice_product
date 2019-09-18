package com.pinkoi.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.pinkoi.product.data.Product

class ProductActivity : AppCompatActivity() {
  private val productImage: ImageView by lazy {
    findViewById<ImageView>(R.id.product_image)
  }
  private val favImage: ImageView by lazy {
    findViewById<ImageView>(R.id.fav_image)
  }
  private val nameText: TextView by lazy {
    findViewById<TextView>(R.id.name_text)
  }
  private val priceText: TextView by lazy {
    findViewById<TextView>(R.id.price_text)
  }
  private var isFav: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_product)

    val args = intent.extras
    val product = args?.getSerializable("product") as Product

    productImage.setImageResource(product.imageId)

    favImage.let { image ->
      image.setOnClickListener {
        if (isFav) {
          image.setImageResource(R.drawable.ic_fav_off)
        } else {
          image.setImageResource(R.drawable.ic_fav_on)
        }
        isFav = !isFav
      }
    }

    nameText.text = product.name

    priceText.text = getString(R.string.price, product.price)
  }
}
