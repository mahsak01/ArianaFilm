package com.mgpersia.androidbox.Fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.databinding.FragmentLoginPhoneDialogBinding
import com.mgpersia.androidbox.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast

class LogInPhoneDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentLoginPhoneDialogBinding

    private var selected_country_code: String? = null

    private var loadingFragment: LoadingFragment? = null

    private var phone:Editable?=null

    private var idCountry:Int?=null

    private val mainViewModel: MainViewModel by viewModel()


    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingFragment = LoadingFragment()
        loadingFragment?.show(requireActivity().supportFragmentManager, null)
        mainViewModel.getAllCountries()
        setListeners()
        setObserver()
    }

    private fun setObserver() {
        mainViewModel.countriesLiveData.observe(this) {
            loadingFragment!!.dismiss()
        }
        this.mainViewModel.errorHandlingLiveData.observe(this) {
            loadingFragment!!.dismiss()
            Toast.makeText(requireContext(), "خطا در دریافت اطلاعات از سرور", Toast.LENGTH_LONG)
                .showCustomToast(
                    "خطا در دریافت اطلاعات از سرور",
                    requireActivity(),
                    ToastStatus.ERROR
                )
        }
        mainViewModel.checkLocationLiveData.observe(this){
            if (checkIran()){
                if (it.country_code2=="IR")
                    mainViewModel.getOpt(phone.toString(), idCountry!!)
                else
                    Toast.makeText(requireContext(), "لطفا ابتدا vpn خود را خاموش کرده و مجدد امتحان کنید", Toast.LENGTH_LONG)
                        .showCustomToast(
                            "لطفا ابتدا vpn خود را خاموش کرده و مجدد امتحان کنید",
                            requireActivity(),
                            ToastStatus.ERROR
                        )
            }
            else{
                mainViewModel.getOpt(phone.toString(), idCountry!!)
            }
        }
        mainViewModel.getOptLiveData.observe(this){
            if (it!=null){
                loadingFragment!!.dismiss()
                dismiss()
                val logInCodeDialogFragment = LogInCodeDialogFragment(phone.toString(),
                    it.is_signup!!,idCountry.toString(),checkIran())
                logInCodeDialogFragment.show(requireFragmentManager(), "bottomSheetDialog")
            }

        }
    }


    private fun setListeners() {
            this.binding.fragmentLoginPhoneDialogPhoneCcp.setOnCountryChangeListener {
                selected_country_code =
                    binding.fragmentLoginPhoneDialogPhoneCcp.selectedCountryNameCode
            }
        this.binding.fragmentLoginPhoneDialogLoginBtn.setOnClickListener {
             phone = binding.fragmentLoginPhoneDialogPhoneEditText.text
            if (!phone.isNullOrEmpty()) {
                 idCountry=searchCountry()
                if (idCountry!=-1){
                    mainViewModel.checkLocationInformation()
                    loadingFragment = LoadingFragment()
                    loadingFragment?.show(requireActivity().supportFragmentManager, null)

                }


            } else {
                Toast.makeText(
                    requireContext(),
                    "لطفا شماره موبایل رو وارد کنید",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


    }

    private fun checkIran():Boolean{
        for (it in mainViewModel.countriesLiveData.value!!) {
            if (it.country_code == "IR")
                return true
        }
        return false
    }

    private fun searchCountry():Int{
        for (it in mainViewModel.countriesLiveData.value!!) {
            if (it.country_code == selected_country_code)
                return it.id!!
        }
        return -1
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.fragment_login_phone_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

}