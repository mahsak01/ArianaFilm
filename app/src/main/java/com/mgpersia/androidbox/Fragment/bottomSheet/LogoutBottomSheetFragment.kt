package com.mgpersia.androidbox.Fragment.bottomSheet

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.model.information.DeleteDeviceInformation
import com.mgpersia.androidbox.databinding.FragmentLogoutBottomSheetBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogoutBottomSheetFragment(private val eventListener: EventListener) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLogoutBottomSheetBinding
    private val userSettingViewModel: UserSettingViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        setListener()
        setObserver()
    }
    private fun setObserver(){

    }

    private fun setListener() {
        this.binding.fragmentLogoutBottomSheetOkBtn.setOnClickListener {
            userSettingViewModel.deleteDevice(
                DeleteDeviceInformation(
                    UserContainer.user!!.phone, Settings.Secure.getString(
                        requireContext().contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                )
            )

            eventListener.close()
            dismiss()
//            dismiss()
//            this.requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentLogoutBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    interface EventListener{
        fun close()
    }
}