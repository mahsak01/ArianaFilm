package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.TicketItemSettingAdapter
import com.mgpersia.androidbox.data.model.Ticket
import com.mgpersia.androidbox.databinding.ActivityTicketsSettingBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketsSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicketsSettingBinding
    private val userSettingViewModel: UserSettingViewModel by viewModel()
    private var loadingFragment: LoadingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tickets_setting)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        loadingFragment = LoadingFragment()
        loadingFragment?.show(supportFragmentManager, null)
        Handler(mainLooper)
            .postDelayed({
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            }, (30000))
        userSettingViewModel.getAllTicket()
        setObserver()
        setListener()

    }

    private fun setListener() {
        this.binding.ActivityTicketsSettingBackBtn.setOnClickListener {
            this.onBackPressed()
        }

        this.binding.ActivityTicketsSettingAddBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddTicketActivity::class.java
                ).apply {})
        }
    }

    private fun clickTicket(ticket: Ticket) {
        startActivity(
            Intent(
                this,
                TicketDescriptionSettingActivity::class.java
            ).apply {
                putExtra(
                    "ticket",
                    ticket
                )
            })
    }

    private fun setObserver() {

        userSettingViewModel.allTicketsInformationLiveData.observe(this) {
            if (!it.results!!.isNullOrEmpty()) {
                this.binding.ActivityTicketsSettingEmptyLayout.root.visibility = View.GONE
                this.binding.ActivityTicketsSettingTicketsRv.visibility = View.VISIBLE
                binding.ActivityTicketsSettingTicketsRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                var adapter = TicketItemSettingAdapter(it.results as List<Ticket>,
                    object : TicketItemSettingAdapter.EventListener {
                        override fun click(ticket: Ticket) {
                            clickTicket(ticket)
                        }

                    })
                binding.ActivityTicketsSettingTicketsRv.adapter = adapter
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()

            } else {
                this.binding.ActivityTicketsSettingEmptyLayout.root.visibility = View.VISIBLE
                this.binding.ActivityTicketsSettingTicketsRv.visibility = View.GONE
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()

            }
        }
    }

}