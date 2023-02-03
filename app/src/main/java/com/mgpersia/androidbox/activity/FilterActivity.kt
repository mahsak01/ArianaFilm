package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.Fragment.bottomSheet.FilterOptionBottomSheetFragment
import com.mgpersia.androidbox.Fragment.bottomSheet.FilterOptionYearBottomSheetFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FilterSelectItemAdapter
import com.mgpersia.androidbox.common.MainContainer
import com.mgpersia.androidbox.data.model.FilterBottomSheet
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.databinding.ActivityFilterBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor

class FilterActivity : AppCompatActivity(), FilterOptionBottomSheetFragment.EventListener {

    private lateinit var binding: ActivityFilterBinding
    private var loadingFragment: LoadingFragment? = null
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
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
        sharedViewModel.getAllCategoryGenre()
        setObserver()
        setListener()
        setInformation()

    }

    private fun setInformation() {
        val list = ArrayList<FilterOptionBottomSheet>()
        for (item in MainContainer.filterItems)
            for (item2 in item.items)
                if (item.itemSelect.contains(item2.id))
                    list.add(item2)
        if (MainContainer.startYear != null && MainContainer.endYear != null)
            list.add(
                FilterOptionBottomSheet(
                    -1,
                    "${MainContainer.startYear}-${MainContainer.endYear}"
                )
            )

        if (MainContainer.filterItems.size != 0) {
            this.binding.activityFilterFilterItemRv.visibility = View.VISIBLE
            val displayMetrics = this.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = floor(dpWidth / 150).toInt()
            this.binding.activityFilterFilterItemRv.layoutManager =
                GridLayoutManager(this, size, GridLayoutManager.VERTICAL, false)

            val adapter = FilterSelectItemAdapter(list, object :
                FilterSelectItemAdapter.EventListener {
                override fun delete(filterOptionBottomSheet: FilterOptionBottomSheet) {
                    if (filterOptionBottomSheet.id == -1) {
                        MainContainer.endYear = null
                        MainContainer.startYear = null
                        list.remove(filterOptionBottomSheet)
                    } else {
                        for (item in MainContainer.filterItems)
                            for (item2 in item.items)
                                if (item2.value == filterOptionBottomSheet.value) {
                                    item.itemSelect.remove(filterOptionBottomSheet.id)
                                    MainContainer.updateFilterItems(item)
                                    list.remove(item2)
                                }
                    }

                }
            })
            binding.activityFilterFilterItemRv.adapter = adapter

        } else {
            this.binding.activityFilterFilterItemRv.visibility = View.GONE
        }
    }


    private fun setObserver() {
        this.sharedViewModel.categoryGenreLiveData.observe(this) {
            val list = ArrayList<FilterOptionBottomSheet>()
            for (item in it.results!!)
                list.add(FilterOptionBottomSheet(item!!.id!!, item.name!!))

            val selectList = ArrayList<Int>()
            val generalId = intent.getIntExtra("generalId", -1);
            if (generalId != -1)
                selectList.add(generalId)
            MainContainer.filterItems.add(
                FilterBottomSheet(
                    "ژانر",
                    list,
                    selectList
                )
            )
            sharedViewModel.getAllCountriesFilms()
        }
        this.sharedViewModel.countriesFilmsLiveData.observe(this) {
            val list = ArrayList<FilterOptionBottomSheet>()
            for (item in it!!)
                list.add(FilterOptionBottomSheet(item!!.id!!, item.name!!))
            MainContainer.filterItems.add(
                FilterBottomSheet(
                    "کشور",
                    list,
                    ArrayList()
                )
            )
            MainContainer.filterItems.add(
                FilterBottomSheet(
                    "زیرنویس",
                    arrayListOf(
                        FilterOptionBottomSheet(0, "دوبله"),
                        FilterOptionBottomSheet(1, "زیرنویس فارسی"),
                        FilterOptionBottomSheet(2, "زیرنویس انگلیسی"),
                        FilterOptionBottomSheet(3, "زبان اصلی"),
                    ),
                    ArrayList()
                )
            )
            sharedViewModel.getAgeInformation()
        }
        this.sharedViewModel.getAgeInformationLiveData.observe(this) {
            val list = ArrayList<FilterOptionBottomSheet>()
            for (item in it.results!!)
                list.add(FilterOptionBottomSheet(item!!.id!!, item.name!!))
            MainContainer.filterItems.add(
                FilterBottomSheet(
                    "رده سنی",
                    list,
                    ArrayList()
                )
            )
            update()
            loadingFragment!!.dismiss()
        }

    }


    private fun setListener() {
        this.binding.root.setOnKeyListener { _, p1, _ ->
            if (p1 != KeyEvent.ACTION_DOWN) {
                this.binding.activityFilterBackBtn.callOnClick()
            }
            true
        }
        this.binding.activityFilterFilterBtn.setOnClickListener {
            this.binding.activityFilterBackBtn.callOnClick()
        }
        this.binding.activityFilterBackBtn.setOnClickListener {
            MainContainer.setFilter=true
            onBackPressed()
            startActivity(
                Intent(
                    this,
                    SearchActivity::class.java
                ).apply {})

        }
        this.binding.activityFilterGenreCL.setOnClickListener {
            val bottomSheetDialog =
                FilterOptionBottomSheetFragment(MainContainer.filterItems, false, 0, this)
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }
        this.binding.activityFilterCountryCl.setOnClickListener {
            val bottomSheetDialog =
                FilterOptionBottomSheetFragment(MainContainer.filterItems, false, 1, this)
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }
        this.binding.activityFilterSubtitleCl.setOnClickListener {
            val bottomSheetDialog =
                FilterOptionBottomSheetFragment(MainContainer.filterItems, false, 2, this)
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }
        this.binding.activityFilterAgeCl.setOnClickListener {
            val bottomSheetDialog =
                FilterOptionBottomSheetFragment(MainContainer.filterItems, false, 3, this)
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }

        this.binding.activityFilterYearCl.setOnClickListener {
            val bottomSheetDialog = FilterOptionYearBottomSheetFragment(this)
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }

        this.binding.activityFilterDeleteFilterBtn.setOnClickListener {
            for (item in MainContainer.filterItems) {
                item.itemSelect.clear()
                MainContainer.updateFilterItems(item)
                update()
            }
        }

    }

    override fun update() {
        setInformation()
    }

    interface EventListener {
        fun search()
    }
}