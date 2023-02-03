package com.mgpersia.androidbox.Fragment.dialog

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
import com.mgpersia.androidbox.data.model.information.RegisterUserInformation
import com.mgpersia.androidbox.databinding.FragmentLoginCompleteInformationDialogBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast

class LogInCompleteInformationDialogFragment(
    private val phone: String,
    private val token: String,
    private val idCountry: String
) : DialogFragment() {
    private lateinit var binding: FragmentLoginCompleteInformationDialogBinding

    private var loadingFragment: LoadingFragment? = null

    private val sharedViewModel: SharedViewModel by viewModel()

    private val userSettingViewModel: UserSettingViewModel by viewModel()

    override fun onResume() {
        super.onResume()

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        setListeners()
        setObserver()
    }

    private fun setObserver() {
        userSettingViewModel.getUserLiveData.observe(this) {
            val preferences: SharedPreferences = requireContext().getSharedPreferences("", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  preferences.edit()
            editor.putString("token",TokenContainer.token)
            editor.putString("avatar", it!!.avatar.toString())
            editor.putString("email",it!!.email.toString())
            editor.putString("first_name",it!!.first_name.toString())
            editor.putString("last_name",it!!.last_name.toString())
            editor.putString("phone",it!!.phone.toString())
            editor.putString("plan_id",it!!.plan_id.toString())
            editor.putString("is_vip",it!!.is_vip.toString())
            editor.apply()
            editor.commit()
            dismiss()
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
        sharedViewModel.registerUserInformationLiveData.observe(this) {
            loadingFragment!!.dismiss()
            if (it.username != null) {
                Toast.makeText(
                    requireContext(),
                    it.username[0],
                    Toast.LENGTH_SHORT
                ).show()
            } else if (it.access_token != null) {
                TokenContainer.update(it.access_token)
                userSettingViewModel.getUserProfile()

                dismiss()
                val bottomSheetDialog = SignUpBottomSheetFragment(true)
                bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
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


    private fun setListeners() {
        this.binding.fragmentLoginCompleteInformationDialogLoginBtn.setOnClickListener {
            val firstName = binding.fragmentLoginCompleteInformationDialogFirstNameTI.text
            val lastName = binding.fragmentLoginCompleteInformationDialogLastNameTI.text
            val email = binding.fragmentLoginCompleteInformationDialogEmailTI.text
            val username = binding.fragmentLoginCompleteInformationDialogUserNameTI.text


            if (!firstName.isNullOrEmpty() || !lastName.isNullOrEmpty() || !email.isNullOrEmpty() || !username.isNullOrEmpty()) {
                val model =Build.MANUFACTURER+" " + Build.MODEL

                sharedViewModel.registerUser(
                    RegisterUserInformation(
                        phone,
                        token,
                        username.toString(),
                        idCountry,
                        firstName.toString(),
                        lastName.toString(),
                        email.toString(),
                        model,
                        "2",
                        Settings.Secure.getString(
                            requireContext().contentResolver,
                            Settings.Secure.ANDROID_ID
                        ).toString()

                )
                )
                loadingFragment = LoadingFragment()
                loadingFragment?.show(requireActivity().supportFragmentManager, null)

            } else {
                Toast.makeText(
                    requireContext(),
                    "لطفا اطلاعات را کامل وارد کنید",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.fragment_login_complete_information_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        this.isCancelable = false
        return dialogBuilder.create()
    }

}