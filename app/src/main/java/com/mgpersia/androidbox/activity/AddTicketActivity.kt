package com.mgpersia.androidbox.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.Department
import com.mgpersia.androidbox.data.model.enum.PriorityTicket
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.data.model.information.AddTicketInformation
import com.mgpersia.androidbox.data.model.information.GetAllDepartmentsInformation
import com.mgpersia.androidbox.databinding.ActivityAddTicketBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast
import java.io.File

class AddTicketActivity : AppCompatActivity(), PickiTCallbacks {

    private lateinit var binding: ActivityAddTicketBinding
    private val userSettingViewModel: UserSettingViewModel by viewModel()
    private var loadingFragment: LoadingFragment? = null
    private var pickiT: PickiT? = null

    private var filePath: String? = null

    private val filePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    it.data?.let { it1 ->
                        pickiT?.getPath(it1, Build.VERSION.SDK_INT)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_ticket)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        pickiT = PickiT(this, this, this)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        loadingFragment = LoadingFragment()
        loadingFragment?.show(supportFragmentManager, null)
        Handler(mainLooper)
            .postDelayed({
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            }, (30000))
        userSettingViewModel.getAllDepartments()
        setObserver()
        setListener()
        setInformation()

    }

    private fun setInformation() {
        val items = resources.getStringArray(R.array.prioritys)
        val adapter = ArrayAdapter(
            this,
            R.layout.layout_dropdown_item, items
        )
        this.binding.ActivityAddTicketSettingPriorityTI.adapter = adapter
    }

    private fun setListener() {
        this.binding.ActivityAddTicketSettingBackBtn.setOnClickListener {
            this.onBackPressed()
        }

        this.binding.ActivityAddTicketSettingFileBtn.setOnClickListener {
            filePickerLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).also {
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                it.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                it.putExtra("type", "*")
                it.type = "*/*"
            })
        }

        this.binding.ActivityAddTicketSettingAddBtn.setOnClickListener {

            if (this.binding.ActivityAddTicketSettingTitleTI.text.toString().isNotEmpty()) {
                if (this.binding.ActivityAddTicketSettingMessageTI.text.toString().isNotEmpty()) {
                    if (filePath != null)
                        userSettingViewModel.addTicket(
                            AddTicketInformation(
                                searchDepartment()!!.id,
                                this.binding.ActivityAddTicketSettingMessageTI.text.toString(),
                                this.binding.ActivityAddTicketSettingTitleTI.text.toString(),
                                searchPriority().value
                            ), File(filePath)
                        )
                    else
                        userSettingViewModel.addTicket(
                            AddTicketInformation(
                                searchDepartment()!!.id,
                                this.binding.ActivityAddTicketSettingMessageTI.text.toString(),
                                this.binding.ActivityAddTicketSettingTitleTI.text.toString(),
                                searchPriority().value
                            ), null
                        )

                } else
                    Toast.makeText(this, "پیام تیکت رو وارد کنید", Toast.LENGTH_LONG)
                        .showCustomToast(
                            "موضوع تیکت رو وارد کنید",
                            this,
                            ToastStatus.ERROR
                        )

            } else
                Toast.makeText(this, "موضوع تیکت رو وارد کنید", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "موضوع تیکت رو وارد کنید",
                        this,
                        ToastStatus.ERROR
                    )

        }

    }

    private fun searchDepartment(): Department? {
        for (item in (userSettingViewModel.allDepartmentsInformationLiveData.value as GetAllDepartmentsInformation).results!!)
            if (item!!.name == this.binding.ActivityAddTicketSettingDepartmentTI.selectedItem.toString())
                return item

        return null
    }

    private fun searchPriority(): PriorityTicket {
        return when (this.binding.ActivityAddTicketSettingPriorityTI.selectedItem) {
            "کم" -> PriorityTicket.LOW
            "متوسط" -> PriorityTicket.MEDIUM
            "زیاد" -> PriorityTicket.HIGH
            else -> PriorityTicket.LOW
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {

        userSettingViewModel.allDepartmentsInformationLiveData.observe(this) {
            if (loadingFragment != null && loadingFragment!!.showsDialog)
                loadingFragment!!.dismiss()
            val items =
                (userSettingViewModel.allDepartmentsInformationLiveData.value as GetAllDepartmentsInformation).results!!.stream()
                    .map { item -> item!!.name }.toArray()
            val adapter = ArrayAdapter(
                this,
                R.layout.layout_dropdown_item, items
            )
            this.binding.ActivityAddTicketSettingDepartmentTI.adapter = adapter
        }

        userSettingViewModel.addTicketLiveData.observe(this) {
            Toast.makeText(this, "تیکت اضافه شد", Toast.LENGTH_LONG)
                .showCustomToast(
                    "تیکت اضافه شد",
                    this,
                    ToastStatus.OK
                )
            this.onBackPressed()
        }
    }

    override fun PickiTonUriReturned() {
        TODO("Not yet implemented")
    }

    override fun PickiTonStartListener() {
        TODO("Not yet implemented")
    }

    override fun PickiTonProgressUpdate(progress: Int) {
        TODO("Not yet implemented")
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        filePath = path
    }

    override fun PickiTonMultipleCompleteListener(
        paths: ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        TODO("Not yet implemented")
    }
}