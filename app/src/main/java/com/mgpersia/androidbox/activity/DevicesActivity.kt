package com.mgpersia.androidbox.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.DeviceItemSettingAdapter
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.model.Device
import com.mgpersia.androidbox.data.model.information.DeleteDeviceInformation
import com.mgpersia.androidbox.databinding.ActivityDevicesBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DevicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDevicesBinding
    private val userSettingViewModel: UserSettingViewModel by viewModel()
    private var loadingFragment: LoadingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_devices)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        loadingFragment = LoadingFragment()
        loadingFragment?.show(this.supportFragmentManager, null)
        Handler(mainLooper)
            .postDelayed({
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            }, (30000))
        userSettingViewModel.getAllDevices()
        setObserver()
        setListener()

    }

    private fun setListener() {
        this.binding.ActivityDevicesSettingBackBtn.setOnClickListener {
            this.onBackPressed()
        }
    }

    private fun setObserver() {
        userSettingViewModel.allDevicesInformationLiveData.observe(this) {
            if (!it.results!!.isNullOrEmpty()) {
                this.binding.ActivityDevicesSettingEmptyLayout.root.visibility = View.GONE
                this.binding.ActivityDevicesSettingDevicesRv.visibility = View.VISIBLE
                binding.ActivityDevicesSettingDevicesRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                val adapter = DeviceItemSettingAdapter(
                    it.results as List<Device>,
                    resources,
                    object : DeviceItemSettingAdapter.EventListener {
                        override fun deleteDevice(device: Device) {
                            loadingFragment = LoadingFragment()
                            loadingFragment?.show(supportFragmentManager, null)
                            Handler(mainLooper)
                                .postDelayed({
                                    if (loadingFragment != null && loadingFragment!!.showsDialog)
                                        loadingFragment!!.dismiss()
                                }, (30000))
                            userSettingViewModel.deleteDevice(
                                DeleteDeviceInformation(
                                    UserContainer.user!!.phone,
                                    device.mac_address
                                )
                            )
                        }

                    })
                binding.ActivityDevicesSettingDevicesRv.adapter = adapter
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            } else {
                this.binding.ActivityDevicesSettingEmptyLayout.root.visibility = View.VISIBLE
                this.binding.ActivityDevicesSettingDevicesRv.visibility = View.GONE
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            }

        }

        userSettingViewModel.deleteDeviceInformationLiveData.observe(this) {
            userSettingViewModel.getAllDevices()
            Toast.makeText(this, "دستگاه با موفقیت پاک شد", Toast.LENGTH_LONG).show()
        }
    }


}