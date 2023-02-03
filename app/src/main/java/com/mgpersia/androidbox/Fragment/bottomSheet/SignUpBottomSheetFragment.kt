package com.mgpersia.androidbox.Fragment.bottomSheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.databinding.FragmentSignupBottomSheetBinding

class SignUpBottomSheetFragment(
    private val isSignUp:Boolean=true
):
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSignupBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        addListener()
        setInformation()
    }

    private fun setInformation(){
        if (!isSignUp){
            this.binding.fragmentSignUpBottomSheetTitleTv.text = "ورود موفق"
            this.binding.fragmentSignUpBottomSheetBodyTv.text = "برای مشاهده فیلم ها میتونید اشتراک اپ و وب سایت رو تهیه کنید"

        }
    }

    fun addListener() {
        this.binding.fragmentSignUpBottomSheetOkBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentSignupBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

}