package com.pinkoi.product

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.gson.Gson
import com.pinkoi.product.data.ApiResult
import com.pinkoi.product.data.Product
import kotlinx.android.synthetic.main.activity_product_item.view.nameText
import kotlinx.android.synthetic.main.activity_product_item.view.productImage
import kotlinx.android.synthetic.main.activity_product_text_item.view.productNameText
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Runnable

class ProductActivity : AppCompatActivity() {
  private val productImage: ImageView by lazy {
    findViewById<ImageView>(R.id.productImage)
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
  private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

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

    coroutineScope.launch {
//      val apiResult = withContext(Dispatchers.IO) {
//        val client = OkHttpClient()
//        val request = Request.Builder()
//          .url("https://course.aids61517.tw/product.php")
//          .build()
//
//        val response = client.newCall(request).execute()
//        val result = response.body?.string()!!
//        Log.d("ProductActivity", "response = $result")
//        Gson().fromJson(result, ApiResult::class.java)
//      }


      val apiResultDeferred1 = coroutineScope.async(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
          .url("https://course.aids61517.tw/product.php")
          .build()

        val response = client.newCall(request).execute()
        val result = response.body?.string()!!
        Log.d("ProductActivity", "response = $result")
        Gson().fromJson(result, ApiResult::class.java)
      }
      val apiResultDeferred2 = coroutineScope.async(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
          .url("https://course.aids61517.tw/product.php")
          .build()

        val response = client.newCall(request).execute()
        val result = response.body?.string()!!
        Log.d("ProductActivity", "response = $result")
        Gson().fromJson(result, ApiResult::class.java)
      }

      val apiResult = apiResultDeferred1.await()
      val apiResult2 = apiResultDeferred2.await()


      // update ui
      (recyclerView.adapter as? ProductAdapter)?.setData(apiResult.result)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    coroutineScope.cancel()
  }
}

private class ProductAdapter(
  private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  companion object {
    const val TYPE_IMAGE = 1
    const val TYPE_TEXT = 2
  }

  private var productList: List<Product> = emptyList()

  fun setData(productList: List<Product>) {
    this.productList = productList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    Log.d("ProductAdapter", "onCreateViewHolder viewType $viewType")
    val inflater = LayoutInflater.from(context)
    return if (viewType == TYPE_IMAGE) {
      val productItemView = inflater.inflate(R.layout.activity_product_item, parent, false)
      ProductItemViewHolder(productItemView)
    } else {
      val productItemView = inflater.inflate(R.layout.activity_product_text_item, parent, false)
      ProductItemTextViewHolder(productItemView)
    }
  }

  override fun getItemCount(): Int {
    return productList.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    Log.d("ProductAdapter", "onBindViewHolder position = $position, holder = $holder")
    val product = productList[position]
    if (holder is ProductItemViewHolder) {
      holder.nameText.apply {
        text = product.name
      }

      holder.productImage.apply {
        setImageResource(product.imageId)
      }
    } else if (holder is ProductItemTextViewHolder) {
      holder.nameText.apply {
        text = product.name
      }
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (position % 2 == 0) {
      TYPE_IMAGE
    } else {
      TYPE_TEXT
    }
  }

  class ProductItemViewHolder(
    itemView: View
  ) : RecyclerView.ViewHolder(itemView) {
    val productImage: ImageView = itemView.productImage
    val nameText: TextView = itemView.nameText
  }

  class ProductItemTextViewHolder(
    itemView: View
  ) : RecyclerView.ViewHolder(itemView) {
    val nameText: TextView = itemView.productNameText
  }
}