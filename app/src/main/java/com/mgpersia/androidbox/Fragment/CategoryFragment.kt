package com.mgpersia.androidbox.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mgpersia.androidbox.Fragment.bottomSheet.LogoutBottomSheetFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.activity.CategoryAllFilmActivity
import com.mgpersia.androidbox.adapter.CategoryItemAdapter
import com.mgpersia.androidbox.data.model.General
import com.mgpersia.androidbox.databinding.FragmentCategoryBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import convertDpToPixel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.system.exitProcess


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val sharedViewModel: SharedViewModel by viewModel()
    private var isLogOut = 0


    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        sharedViewModel.getAllCategoryGenre()

        setObserver()
        setListener()
        setFocus()
        this.binding.fragmentCategoryBackBtn.requestFocus()

    }

    private fun setFocus() {
        this.binding.fragmentCategoryBackBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentCategoryBackBtn.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.fragmentCategoryBackBtn.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
    }

    private fun setListener() {
        this.binding.root.setOnKeyListener { _, p1, _ ->
            if (p1 != KeyEvent.ACTION_DOWN) {
                if (isLogOut == 0) {
                    val bottomSheetDialog =
                        LogoutBottomSheetFragment(object : LogoutBottomSheetFragment.EventListener {
                            override fun close() {
                                // on below line we are finishing activity.
                                activity!!.finish()

                                // on below line we are exiting our activity
                                exitProcess(0)
                            }

                        })
                    bottomSheetDialog.show(childFragmentManager, null)
                    isLogOut++
                } else {
                    isLogOut = 0
                }

            }
            true
        }
    }

    private fun onClickItem(tag: String, genre: String, title: String) {
        startActivity(
            Intent(
                requireActivity(),
                CategoryAllFilmActivity::class.java
            ).apply {
                putExtra(
                    "title",
                    title
                )
                putExtra(
                    "tag",
                    tag
                )
                putExtra(
                    "genre",
                    genre
                )
            })
    }

    private fun setObserver() {
        sharedViewModel.categoryGenreLiveData.observe(this) {
            sharedViewModel.getAllCategoryTag()
            this.binding.fragmentCategoryItemGenerelGvShimmer.stopShimmer()
            this.binding.fragmentCategoryItemGenerelGvShimmer.visibility = View.GONE
            this.binding.fragmentCategoryItemGenerelGv.visibility = View.VISIBLE
            if (!it.results!!.isNullOrEmpty()) {
                val displayMetrics = requireContext().resources.displayMetrics
                val dpWidth = displayMetrics.widthPixels / displayMetrics.density
                val size = floor(dpWidth / 250).toInt()
                this.binding.fragmentCategoryItemGenerelGv.numColumns = size
                var adapter = CategoryItemAdapter(
                    it.results as List<General>,
                    object : CategoryItemAdapter.EventListener {
                        override fun click(general: General) {
                            onClickItem("", general.id.toString(), general.name.toString())
                        }
                    }, requireContext(), size,
                    this.binding.fragmentCategoryItemTagGv.id,
                    this.binding.fragmentCategoryBackBtn.id,
                    this.binding.fragmentCategoryBackBtn.id,
                    this.binding.fragmentCategoryItemTagGv.id
                )
                val layoutParams = this.binding.fragmentCategoryItemGenerelGv.layoutParams
                layoutParams.height = convertDpToPixel(
                    (ceil(((it.count!! + 1) / size).toDouble() + 1).toInt() * 160 + 20).toFloat(),
                    requireContext()
                ).toInt()
                this.binding.fragmentCategoryItemGenerelGv.layoutParams = layoutParams
                binding.fragmentCategoryItemGenerelGv.adapter = adapter
            } else {
                this.binding.fragmentCategoryItemGenerelGv.visibility = View.GONE
                this.binding.fragmentCategoryItemGenerelTv.visibility = View.GONE

            }
        }
        sharedViewModel.categoryTagLiveData.observe(this) {
            this.binding.fragmentCategoryItemTagShimmer.stopShimmer()
            this.binding.fragmentCategoryItemTagShimmer.visibility = View.GONE
            this.binding.fragmentCategoryItemTagGv.visibility = View.VISIBLE
            if (!it.results!!.isNullOrEmpty()) {
                val displayMetrics = requireContext().resources.displayMetrics
                val dpWidth = displayMetrics.widthPixels / displayMetrics.density
                val size = floor(dpWidth / 250).toInt()
                this.binding.fragmentCategoryItemTagGv.numColumns = size
                var adapter = CategoryItemAdapter(it.results as List<General>,
                    object : CategoryItemAdapter.EventListener {
                        override fun click(general: General) {
                            onClickItem(general.id.toString(), "", general.name.toString())

                        }

                    }, requireContext(),
                    size,
                    -1,
                    this.binding.fragmentCategoryItemTagGv.id,
                    this.binding.fragmentCategoryItemTagGv.id,
                    -1
                )
                val layoutParams = this.binding.fragmentCategoryItemTagGv.layoutParams
                layoutParams.height = convertDpToPixel(
                    (ceil(((it.count!! + 1) / size).toDouble() + 1).toInt() * 160 + 20).toFloat(),
                    requireContext()
                ).toInt()
                this.binding.fragmentCategoryItemTagGv.layoutParams = layoutParams
                binding.fragmentCategoryItemTagGv.adapter = adapter
            } else {
                this.binding.fragmentCategoryItemTagGv.visibility = View.GONE
                this.binding.fragmentCategoryItemTagTv.visibility = View.GONE
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}