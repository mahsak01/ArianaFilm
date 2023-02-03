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
import com.mgpersia.androidbox.adapter.SeasonOptionItemSettingAdapter
import com.mgpersia.androidbox.data.model.FilterBottomSheet
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.databinding.FragmentFilterOptionBottomSheetBinding

class SeasonOptionBottomSheetFragment(
    private val item: List<FilterBottomSheet>,
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
            SeasonOptionItemSettingAdapter(item[index].items, item[index].itemSelect,
                object : SeasonOptionItemSettingAdapter.EventListener {
                    override fun click(item: FilterOptionBottomSheet) {
                        this@SeasonOptionBottomSheetFragment.item[this@SeasonOptionBottomSheetFragment.index].itemSelect.add(
                            item.id
                        )
                        eventListener.update(item)

                    }

                    override fun delete(item: FilterOptionBottomSheet) {
                        this@SeasonOptionBottomSheetFragment.item[this@SeasonOptionBottomSheetFragment.index].itemSelect.remove(
                            item.id
                        )
                    }

                })
        binding.fragmentFilterOptionBottomSheetItemRv.adapter = adapter
        this.binding.fragmentFilterOptionBottomSheetNextBtn.visibility = View.GONE
    }


    private fun setListener() {

        this.binding.fragmentFilterOptionBottomSheetCloseBtn.setOnClickListener {
            dismiss()
        }
        this.binding.fragmentFilterOptionBottomSheetOkBtn.setOnClickListener {
            dismiss()
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

    interface EventListener {
        fun update(item: FilterOptionBottomSheet)
    }

}