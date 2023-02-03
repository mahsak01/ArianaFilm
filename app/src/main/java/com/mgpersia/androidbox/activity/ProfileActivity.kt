package com.mgpersia.androidbox.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mgpersia.androidbox.Fragment.bottomSheet.LogoutBottomSheetFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
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
        if (!UserContainer.is_iran)
            this.binding.ActivityProfileSettingDownloaderCl.visibility= View.GONE
    }

    private fun setListener() {
        this.binding.ActivityProfileSettingBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.ActivityProfileSettingSubscriptionCl.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SubscriptionStatusActivity::class.java
                ).apply {})
        }

        this.binding.ActivityProfileSettingProfileCL.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    EditProfileActivity::class.java
                ).apply {})
        }

        this.binding.ActivityProfileSettingNotificationCl.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NotificationActivity::class.java
                ).apply {})
        }

        this.binding.ActivityProfileSettingDevicesCl.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DevicesActivity::class.java
                ).apply {})
        }

        this.binding.ActivityProfileSettingTicketsCl.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    TicketsSettingActivity::class.java
                ).apply {})
        }
        this.binding.ActivityProfileSettingFollowingCl.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FollowingSettingActivity::class.java
                ).apply {})
        }

        this.binding.ActivityProfileSettingLogOutCl.setOnClickListener {
            val bottomSheetDialog =
                LogoutBottomSheetFragment(object : LogoutBottomSheetFragment.EventListener {
                    override fun close() {
                        UserContainer.update(null)
                        TokenContainer.update(null)
                        val preferences: SharedPreferences =
                            getSharedPreferences("", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = preferences.edit()
                        editor.putString("token", null)
                        editor.putString("avatar", null)
                        editor.putString("email", null)
                        editor.putString("first_name", null)
                        editor.putString("last_name", null)
                        editor.putString("phone", null)
                        editor.putString("plan_id", null)
                        editor.putString("is_vip", null)
                        editor.apply()
                        editor.commit()
                        onBackPressed()
                    }

                })
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }

        this.binding.ActivityProfileSettingBookmarksCl.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SavedActivity::class.java
                ).apply {})
        }

        this.binding.ActivityProfileSettingDownloaderCl.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DownloadFilmActivity::class.java
                ).apply {})
        }


    }

}