package com.pinkoi.product

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
  private val recyclerView: RecyclerView by lazy {
    findViewById<RecyclerView>(R.id.recycler_view)
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


    recyclerView.apply {
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

      adapter = ProductAdapter(context)
    }

    val productList = mutableListOf<Product>().apply {
      add(Product("1", 100, R.drawable.product_1))
      add(Product("2", 200, R.drawable.product_2))
      add(Product("3", 300, R.drawable.product_3))
      add(Product("1", 100, R.drawable.product_1))
      add(Product("2", 200, R.drawable.product_2))
      add(Product("3", 300, R.drawable.product_3))
      add(Product("1", 100, R.drawable.product_1))
      add(Product("2", 200, R.drawable.product_2))
      add(Product("3", 300, R.drawable.product_3))
      add(Product("1", 100, R.drawable.product_1))
      add(Product("2", 200, R.drawable.product_2))
      add(Product("3", 300, R.drawable.product_3))
    }
    (recyclerView.adapter as? ProductAdapter)?.setData(productList)
  }
}

private class ProductAdapter(
  private val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private var productList: List<Product> = emptyList()

  fun setData(productList: List<Product>) {
    this.productList = productList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val productItemView = LayoutInflater.from(context).run {
      inflate(R.layout.activity_product_item, parent, false)
    }
    return ProductItemViewHolder(productItemView)
  }

  override fun getItemCount(): Int {
    return productList.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (holder is ProductItemViewHolder) {
      val product = productList[position]
      holder.nameText.apply {
        text = product.name
      }

      holder.productImage.apply {
        setImageResource(product.imageId)
      }
    }
  }

  class ProductItemViewHolder(
    itemView: View
  ): RecyclerView.ViewHolder(itemView) {
    val productImage: ImageView = itemView.findViewById(R.id.product_image)
    val nameText: TextView = itemView.findViewById(R.id.product_name_text)
  }
}