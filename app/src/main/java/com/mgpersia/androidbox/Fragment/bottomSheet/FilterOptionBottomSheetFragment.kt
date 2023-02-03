package com.mgpersia.androidbox.Fragment.bottomSheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FilterOptionItemSettingAdapter
import com.mgpersia.androidbox.common.MainContainer
import com.mgpersia.androidbox.data.model.FilterBottomSheet
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.databinding.FragmentFilterOptionBottomSheetBinding

class FilterOptionBottomSheetFragment(
    private val item: List<FilterBottomSheet>,
    private val is_sort: Boolean,
    private val index: Int,
    private val eventListener: EventListener
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterOptionBottomSheetBinding


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
        this.binding.fragmentFilterOptionBottomSheetTitleTv.text = item[index].title
        binding.fragmentFilterOptionBottomSheetItemRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val adapter =
            FilterOptionItemSettingAdapter(item[index].items, item[index].itemSelect, is_sort,
                object : FilterOptionItemSettingAdapter.EventListener {
                    override fun click(item: FilterOptionBottomSheet) {
                        this@FilterOptionBottomSheetFragment.item[this@FilterOptionBottomSheetFragment.index].itemSelect.add(
                            item.id
                        )

                        if (is_sort) {
                            MainContainer.setSortItems(this@FilterOptionBottomSheetFragment.item[this@FilterOptionBottomSheetFragment.index])
                        } else {
                            MainContainer.updateFilterItems(this@FilterOptionBottomSheetFragment.item[this@FilterOptionBottomSheetFragment.index])
                        }
                        eventListener.update()

                    }

                    override fun delete(item: FilterOptionBottomSheet) {
                        this@FilterOptionBottomSheetFragment.item[this@FilterOptionBottomSheetFragment.index].itemSelect.remove(
                            item.id
                        )

                        if (is_sort) {
                            MainContainer.setSortItems(this@FilterOptionBottomSheetFragment.item[this@FilterOptionBottomSheetFragment.index])
                        } else {
                            MainContainer.updateFilterItems(this@FilterOptionBottomSheetFragment.item[this@FilterOptionBottomSheetFragment.index])
                        }
                        eventListener.update()
                    }

                })
        binding.fragmentFilterOptionBottomSheetItemRv.adapter = adapter

        if (is_sort)
            this.binding.fragmentFilterOptionBottomSheetNextBtn.visibility = View.GONE
    }


    private fun setListener() {

        this.binding.fragmentFilterOptionBottomSheetCloseBtn.setOnClickListener {
            dismiss()
        }
        this.binding.fragmentFilterOptionBottomSheetOkBtn.setOnClickListener {
            dismiss()
        }
        this.binding.fragmentFilterOptionBottomSheetNextBtn.setOnClickListener {
            if (!is_sort) {
                if (index != 3) {
                    dismiss()
                    val bottomSheetDialog =
                        FilterOptionBottomSheetFragment(item, false, index + 1,eventListener)
                    bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
                }else{
                    dismiss()
                    val bottomSheetDialog = FilterOptionYearBottomSheetFragment(eventListener)
                    bottomSheetDialog.show(requireFragmentManager(), "bottomSheetDialog")
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentFilterOptionBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    interface EventListener{
        fun update()
    }

}