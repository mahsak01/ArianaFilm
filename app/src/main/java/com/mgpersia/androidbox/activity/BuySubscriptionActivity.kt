package com.mgpersia.androidbox.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.databinding.ActivityBuySubscriptionBinding

class BuySubscriptionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBuySubscriptionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy_subscription)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        setListener()
    }
    private fun setListener(){
        this.binding.ActivityBuySubscriptionBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.ActivityBuySubscription1mounthCL.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.sizpay.ir")
            startActivity(openURL)
        }
        this.binding.ActivityBuySubscription3mounthCL.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.sizpay.ir")
            startActivity(openURL)
        }
        this.binding.ActivityBuySubscription6mounthCL.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.sizpay.ir")
            startActivity(openURL)
        }
        this.binding.ActivityBuySubscription12mounthCL.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.sizpay.ir")
            startActivity(openURL)
        }
    }

}