package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.databinding.ActivitySubscriptionStatusSettingBinding

class SubscriptionStatusActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySubscriptionStatusSettingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription_status_setting)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }
    override fun onResume() {
        super.onResume()
        setListener()
        setInformation()
    }

    private fun setInformation(){
        if (UserContainer.user!!.is_vip==true){
            this.binding.ActivitySubscriptionStatusSubscriptionFL.visibility= View.VISIBLE
            this.binding.ActivitySubscriptionStatusSubscriptionNotFL.visibility= View.GONE

        }else{
            this.binding.ActivitySubscriptionStatusSubscriptionFL.visibility= View.GONE
            this.binding.ActivitySubscriptionStatusSubscriptionNotFL.visibility= View.VISIBLE

        }

    }

    private fun setListener(){
        this.binding.ActivitySubscriptionStatusBackBtn.setOnClickListener {
            this.onBackPressed()
        }

        this.binding.ActivitySubscriptionStatusBuySubscriptionBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    BuySubscriptionActivity::class.java
                ).apply {})
        }
    }

}