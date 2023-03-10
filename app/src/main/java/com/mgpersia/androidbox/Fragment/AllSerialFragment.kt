package com.mgpersia.androidbox.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.Fragment.bottomSheet.LogoutBottomSheetFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.activity.FilmDetailActivity
import com.mgpersia.androidbox.adapter.FilmItemAllFilmsAdapter
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.databinding.FragmentAllFilmsBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast
import kotlin.math.floor
import kotlin.streams.toList
import kotlin.system.exitProcess


class AllSerialFragment : Fragment() {

    private lateinit var binding: FragmentAllFilmsBinding
    private val sharedViewModel: SharedViewModel by viewModel()
    private var isLogOut = 0
    private var page = 1


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        sharedViewModel.getFilterSerialInformation(
            "",
            "series",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            page.toString(),
            "",
            ""
        )
        setInformation()
        setListener()
        setFocus()
        this.binding.fragmentAllFilmsBackBtn.requestFocus()
    }

    private fun setFocus() {
        this.binding.fragmentAllFilmsBackBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentAllFilmsBackBtn.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.fragmentAllFilmsBackBtn.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setInformation() {
        this.binding.fragmentAllFilmsTitletv.text = "?????????? ????"
    }

    private fun onClickMovie(item: Image) {
        startActivity(
            Intent(
                requireContext(),
                FilmDetailActivity::class.java
            ).apply {
                putExtra("id_film", item.id.toString())
            })
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
        this.sharedViewModel.errorHandlingLiveData.observe(this) {
            Toast.makeText(requireContext(), "?????? ???? ???????????? ?????????????? ???? ????????", Toast.LENGTH_LONG)
                .showCustomToast(
                    "?????? ???? ???????????? ?????????????? ???? ????????",
                    requireActivity(),
                    ToastStatus.ERROR
                )
        }
        this.sharedViewModel.getFilterSerialInformationLiveData.observe(this) {
            binding.fragmentAllFilmsProgressbar.visibility = View.GONE
            this.binding.fragmentAllFilmsItemRvShimmer.stopShimmer()
            this.binding.fragmentAllFilmsItemRvShimmer.visibility = View.GONE
            this.binding.fragmentAllFilmsItemRv.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {
                val displayMetrics = requireContext().resources.displayMetrics
                val dpWidth = displayMetrics.widthPixels / displayMetrics.density
                val size = floor(dpWidth / 150).toInt()
                this.binding.fragmentAllFilmsItemRv.numColumns = size
////                this.binding.fragmentHomeNewFilmRv.layoutManager =
//                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
                val adapter = FilmItemAllFilmsAdapter(
                    requireContext(),
                    it.results!!.stream().map { film ->
                        Image(
                            film.id!!,
                            film.image,
                            film.cover,
                            film.name_en,
                            null,
                            film.is_dubbed,
                            film.has_subtitle,
                            film.imdb_id
                        )
                    }
                        .toList(),
                    object :
                        FilmItemAllFilmsAdapter.EventListener {
                        override fun click(item: Image) {
                            onClickMovie(item)
                        }

                        override fun reload() {
                            if (it.next != null) {
                                page++
                                binding.fragmentAllFilmsProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterFilmInformation(
                                    "",
                                    "series",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    page.toString(),
                                    "",
                                    ""
                                )
                            } else {
                                binding.fragmentAllFilmsProgressbar.visibility = View.GONE

                            }

                        }


                    })

                binding.fragmentAllFilmsItemRv.adapter = adapter

            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllFilmsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}