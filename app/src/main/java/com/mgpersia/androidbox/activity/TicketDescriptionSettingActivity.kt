package com.mgpersia.androidbox.activity

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgpersia.androidbox.BuildConfig
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.AnswerTicketItemAdapter
import com.mgpersia.androidbox.data.Comment
import com.mgpersia.androidbox.data.model.AnswerTicket
import com.mgpersia.androidbox.data.model.Ticket
import com.mgpersia.androidbox.data.model.information.AddCommentInformation
import com.mgpersia.androidbox.databinding.ActivityTicketDescriptionSettingBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import downloadFile
import org.koin.androidx.viewmodel.ext.android.viewModel
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TicketDescriptionSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicketDescriptionSettingBinding
    private var loadingFragment: LoadingFragment? = null

    private val userSettingViewModel: UserSettingViewModel by viewModel()

    private val answerTickets = ArrayList<AnswerTicket>()


    private lateinit var ticket: Ticket
    private val previewRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                RESULT_OK -> {
                    var filename = ticket.attachment.toString().split('/')
                    downloadFile(
                        ticket.attachment.toString(),
                        (filename[filename.size - 1]),
                        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager,
                        this
                    )
                }

                RESULT_CANCELED -> {
                    400
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket_description_setting)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        ticket = intent.getParcelableExtra("ticket")!!
        loadingFragment = LoadingFragment()
        loadingFragment?.show(supportFragmentManager, null)
        Handler(mainLooper)
            .postDelayed({
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            }, (30000))
        userSettingViewModel.getAnswerTicket(ticket.id.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setListener()
        }
        setObserver()
        setInformation()


    }

    private fun setObserver() {
        userSettingViewModel.getAnswerTicketLiveData.observe(this) {
            if (it.count == 0) {
                this.binding.ActivityTicketDescriptionSettingAnswerRv.visibility = View.GONE
            } else {
                answerTickets.clear()
                it.results!!.forEach { item -> answerTickets.add(item!!) }
                val myLinearLayoutManager = object : LinearLayoutManager(this) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
                this.binding.ActivityTicketDescriptionSettingAnswerRv.layoutManager =
                    myLinearLayoutManager
                this.binding.ActivityTicketDescriptionSettingAnswerRv.adapter =
                    AnswerTicketItemAdapter(
                        answerTickets
                    )
            }
            if (loadingFragment != null && loadingFragment!!.showsDialog)
                loadingFragment!!.dismiss()


        }
        userSettingViewModel.addCommentInformationLiveData.observe(this) {
            if (it != null) {
                answerTickets.add(it)
                val myLinearLayoutManager = object : LinearLayoutManager(this) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
                this.binding.ActivityTicketDescriptionSettingAnswerRv.layoutManager =
                    myLinearLayoutManager
                this.binding.ActivityTicketDescriptionSettingAnswerRv.adapter =
                    AnswerTicketItemAdapter(
                        answerTickets
                    )
            }
        }

    }

    private fun setInformation() {
        if (!ticket.subject.isNullOrEmpty())
            this.binding.ActivityTicketDescriptionSettingTitleTI.text =
                Editable.Factory.getInstance().newEditable(ticket.subject)

        if (!ticket.department!!.name.isNullOrEmpty())
            this.binding.ActivityTicketDescriptionSettingDepartmentTI.text =
                ticket.department!!.name

        if (!ticket.status.isNullOrEmpty())
            this.binding.ActivityTicketDescriptionSettingStatusTI.text = ticket.status

        if (!ticket.message.isNullOrEmpty())
            this.binding.ActivityTicketDescriptionSettingMessageTI.text =
                Editable.Factory.getInstance().newEditable(ticket.message)

        if (!ticket.created_at.isNullOrEmpty()) {
            val time = ticket.created_at!!.split('-')
            val pdate =
                PersianDate(
                    Date(
                        time[0].toInt(),
                        time[1].toInt() - 1,
                        time[2].split('T')[0].toInt() - 1
                    )
                )
            val pdformater1 = PersianDateFormat("j F")
            this.binding.ActivityTicketDescriptionSettingDateTI.text = pdformater1.format(pdate)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListener() {
        this.binding.ActivityTicketDescriptionSettingBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.ActivityTicketDescriptionSettingFileBtn.setOnClickListener {
            if (ticket.attachment != null) {
                download()

                Toast.makeText(this, "فایل پیوست در حال دانلود است", Toast.LENGTH_LONG)

            } else
                Toast.makeText(this, "تیکت شامل فایل پیوست نیست", Toast.LENGTH_LONG)
        }

        this.binding.ActivityTicketDescriptionSettingSendIv.setOnClickListener {
            if (!this.binding.ActivityTicketDescriptionSettingCommentTI.text.isNullOrEmpty())
                userSettingViewModel.addCommentTicket(
                    AddCommentInformation(
                        this.binding.ActivityTicketDescriptionSettingCommentTI.text.toString(),
                        ticket.id
                    )
                )
        }

    }

    private fun download() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val filename = ticket.attachment.toString().split('/')
                downloadFile(
                    ticket.attachment.toString(),
                    (filename[filename.size - 1]),
                    getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager,
                    this
                )

            } else {
                val filename = ticket.attachment.toString().split('/')
                downloadFile(
                    ticket.attachment.toString(),
                    (filename[filename.size - 1]),
                    getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager,
                    this
                )
            }


        } else {
            val filename = ticket.attachment.toString().split('/')
            downloadFile(
                ticket.attachment.toString(),
                (filename[filename.size - 1]),
                getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager,
                this
            )
        }
    }


    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts(
            "package",
            BuildConfig.APPLICATION_ID, null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        previewRequest.launch(intent)

    }

}