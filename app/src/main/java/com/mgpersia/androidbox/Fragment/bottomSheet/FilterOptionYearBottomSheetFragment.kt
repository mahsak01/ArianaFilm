package com.mgpersia.androidbox.Fragment.bottomSheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.MainContainer
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.databinding.FragmentFilterOptionYearBottomSheetBinding

class FilterOptionYearBottomSheetFragment(
    private val eventListener: FilterOptionBottomSheetFragment.EventListener
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterOptionYearBottomSheetBinding

    private val selectItems = ArrayList<FilterOptionBottomSheet>()

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
        setInformation()
    }

    private fun setInformation() {
        this.binding.fragmentFilterOptionBottomSheetTitleTv.text = "سال انتشار"
        this.binding.fragmentFilterOptionBottomSheetStartYear.minValue = 1930
        this.binding.fragmentFilterOptionBottomSheetStartYear.maxValue = 2022
        this.binding.fragmentFilterOptionBottomSheetStartYear.wrapSelectorWheel = true
        this.binding.fragmentFilterOptionBottomSheetEndYear.minValue = 1930
        this.binding.fragmentFilterOptionBottomSheetEndYear.maxValue = 2022
        this.binding.fragmentFilterOptionBottomSheetEndYear.wrapSelectorWheel = true

        if (MainContainer.startYear != null)
            this.binding.fragmentFilterOptionBottomSheetStartYear.value =
                MainContainer.startYear!!.toInt()

        if (MainContainer.endYear != null)
            this.binding.fragmentFilterOptionBottomSheetEndYear.value =
                MainContainer.endYear!!.toInt()

    }


    private fun setListener() {

        this.binding.fragmentFilterOptionBottomSheetCloseBtn.setOnClickListener {
            dismiss()
        }
        this.binding.fragmentFilterOptionBottomSheetOkBtn.setOnClickListener {
            MainContainer.updateYear(
                this.binding.fragmentFilterOptionBottomSheetStartYear.value.toString(),
                this.binding.fragmentFilterOptionBottomSheetEndYear.value.toString()
            )
            eventListener.update()
            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentFilterOptionYearBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


}