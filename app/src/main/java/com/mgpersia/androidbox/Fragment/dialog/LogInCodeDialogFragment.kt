package com.mgpersia.androidbox.Fragment.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.Fragment.bottomSheet.SignUpBottomSheetFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.databinding.FragmentLoginCodeDialogBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast


class LogInCodeDialogFragment(
    private val phone: String,
    private val is_signup: Boolean,
    private val idCountry: String,
    private val is_iran:Boolean
) :
    DialogFragment() {
    private lateinit var binding: FragmentLoginCodeDialogBinding

    private var selected_country_code: String? = null

    private var loadingFragment: LoadingFragment? = null

    private val userViewModel: UserSettingViewModel by viewModel()

    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setListeners()
        setObserver()
    }

    private fun setObserver() {
        userViewModel.getUserLiveData.observe(this) {
            val preferences: SharedPreferences = requireContext().getSharedPreferences("", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  preferences.edit()
            editor.putString("token",TokenContainer.token)
            editor.putString("avatar", it!!.avatar.toString())
            editor.putString("email", it.email.toString())
            editor.putString("first_name", it.first_name.toString())
            editor.putString("last_name", it.last_name.toString())
            editor.putString("phone", it.phone.toString())
            editor.putString("plan_id", it.plan_id.toString())
            editor.putString("is_vip", it.is_vip.toString())
            editor.putString("is_iran", is_iran.toString())
            editor.apply()
            editor.commit()
            dismiss()
            val bottomSheetDialog = SignUpBottomSheetFragment(false)
            bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
        }
        this.sharedViewModel.errorHandlingLiveData.observe(this) {
            loadingFragment!!.dismiss()
            Toast.makeText(requireContext(), "خطا در دریافت اطلاعات از سرور", Toast.LENGTH_LONG)
                .showCustomToast(
                    "خطا در دریافت اطلاعات از سرور",
                    requireActivity(),
                    ToastStatus.ERROR
                )
        }

        sharedViewModel.checkOptLiveData.observe(this) {
            loadingFragment!!.dismiss()

            if (is_signup && it.access_token != null) {
                TokenContainer.update(it.access_token)
                userViewModel.getUserProfile()


            } else if (!is_signup) {
                dismiss()
                val loginCompleteInformationDialog =
                    LogInCompleteInformationDialogFragment(phone, it.token!!, idCountry)
                loginCompleteInformationDialog.show(requireFragmentManager(), "bottomSheetDialog")
            } else if (it.message != null) {
                Toast.makeText(
                    requireContext(),
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            } else if (it.detail != null) {
                Toast.makeText(
                    requireContext(),
                    it.detail,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    @SuppressLint("HardwareIds")
    private fun setListeners() {
        this.binding.fragmentLoginCodeDialogLoginBtn.setOnClickListener {
            var code = binding.fragmentLoginCodeDialogCodeEditText.text
            if (!code.isNullOrEmpty()) {
                val model =Build.MANUFACTURER+" " + Build.MODEL
                loadingFragment = LoadingFragment()
                loadingFragment?.show(requireActivity().supportFragmentManager, null)

                sharedViewModel.checkOpt(
                    code.toString(), phone, idCountry, Settings.Secure.getString(
                        requireContext().contentResolver,
                        Settings.Secure.ANDROID_ID
                    ).toString(),
                    model
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "لطفا کد ارسال شده را وارد کنید",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.fragment_login_code_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

}