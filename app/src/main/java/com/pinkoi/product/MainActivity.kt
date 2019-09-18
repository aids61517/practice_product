package com.pinkoi.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.pinkoi.product.data.Product

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val button: Button = findViewById(R.id.click_me_button)
    button.setOnClickListener {
      val product = Product("小魚貼紙", 200, R.drawable.product_1)
      val args = Bundle()
      args.putSerializable("product", product)
      val intent = Intent(this, ProductActivity::class.java)
      intent.putExtras(args)
      startActivity(intent)
    }
  }
}
