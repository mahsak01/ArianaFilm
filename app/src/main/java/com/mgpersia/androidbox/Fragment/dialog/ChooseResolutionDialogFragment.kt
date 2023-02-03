package com.mgpersia.androidbox.Fragment.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.ResolutionOptionItemSettingAdapter
import com.mgpersia.androidbox.data.model.Resolution
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.databinding.LayoutChosseResolutionBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast


class ChooseResolutionDialogFragment(
    private val idFilm: String,
    private val eventListener: EventListener
) :
    DialogFragment() {
    private lateinit var binding: LayoutChosseResolutionBinding

    private var loadingFragment: LoadingFragment? = null

    private val userViewModel: UserSettingViewModel by viewModel()

    private val sharedViewModel: SharedViewModel by viewModel()

    private var i = 0

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingFragment = LoadingFragment()
        loadingFragment?.show(requireActivity().supportFragmentManager, null)
            userViewModel.getAllResolution(idFilm)

        setListeners()
        setObserver()
    }


    private fun setObserver() {
        userViewModel.resolutionInformationLiveData.observe(this) {
            if (it.isEmpty()){
                Toast.makeText(requireContext(), "فیلم این قسمت به زودی آپلود میشود", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "فیلم این قسمت به زودی آپلود میشود",
                        requireActivity(),
                        ToastStatus.ERROR
                    )
                dismiss()
            }
            binding.layoutChooseResolutionItemRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val adapter = ResolutionOptionItemSettingAdapter(
                it,
                i,
                object : ResolutionOptionItemSettingAdapter.EventListener {
                    override fun click(item: Resolution) {
                        i = it.indexOf(item)
                        eventListener.click(item)
                    }

                    override fun delete(item: Resolution) {
                    }

                })
            binding.layoutChooseResolutionItemRv.adapter = adapter
            loadingFragment!!.dismiss()

        }
    }

    @SuppressLint("HardwareIds")
    private fun setListeners() {

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.layout_chosse_resolution,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

    interface EventListener {
        fun click(resolution: Resolution)
    }

}