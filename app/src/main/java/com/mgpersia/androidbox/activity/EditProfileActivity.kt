package com.mgpersia.androidbox.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.model.information.UpdateUserInformation
import com.mgpersia.androidbox.databinding.ActivityEditProfileBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.ArrayList

class EditProfileActivity : AppCompatActivity(), PickiTCallbacks {


    private lateinit var binding: ActivityEditProfileBinding

    private var userProfileAvatar: Uri? = null

    private var pickiT: PickiT? = null

    private var userProfilePath: String? = null


    private var loadingFragment: LoadingFragment? = null

    private val userSettingViewModel: UserSettingViewModel by viewModel()


    private var launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode === RESULT_OK) {
                userProfileAvatar = result.data?.data
                if (userProfileAvatar != null) {
                    this.binding.fragmentEditProfilProfileIv.setImageURI(userProfileAvatar)
                    pickiT?.getPath(userProfileAvatar, Build.VERSION.SDK_INT)

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        pickiT = PickiT(this, this, this)

    }

    override fun onResume() {
        super.onResume()
        setListener()
        setObserver()
        setInformation()
    }

    private fun setInformation() {

        if (!UserContainer.user?.first_name.isNullOrEmpty())
            this.binding.ActivityEditProfileFirstNameTI.text =
                Editable.Factory.getInstance().newEditable(UserContainer.user?.first_name)

        if (!UserContainer.user?.last_name.isNullOrEmpty())
            this.binding.ActivityEditProfileLastNameTI.text =
                Editable.Factory.getInstance().newEditable(UserContainer.user?.last_name)

        if (!UserContainer.user?.phone.isNullOrEmpty())
            this.binding.ActivityEditProfilePhoneTI.text =
                Editable.Factory.getInstance().newEditable(UserContainer.user?.phone)

        if (!UserContainer.user?.email.isNullOrEmpty())
            this.binding.ActivityEditProfileEmailTI.text =
                Editable.Factory.getInstance().newEditable(UserContainer.user?.email)

        if (UserContainer.user?.avatar != null && userProfileAvatar == null)
            Picasso.with(this).load(UserContainer.user?.avatar.toString())
                .fit().centerCrop()
                .into(this.binding.fragmentEditProfilProfileIv)

    }

    private fun setObserver() {
        userSettingViewModel.getUserLiveData.observe(this) {
            Toast.makeText(this, "اطاعات با موفقیت آپدیت شد", Toast.LENGTH_SHORT).show()
            val preferences: SharedPreferences = getSharedPreferences("", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("token", TokenContainer.token)
            editor.putString("avatar", UserContainer.user!!.avatar.toString())
            editor.putString("email", UserContainer.user!!.email.toString())
            editor.putString("first_name", UserContainer.user!!.first_name.toString())
            editor.putString("last_name", UserContainer.user!!.last_name.toString())
            editor.putString("phone", UserContainer.user!!.phone.toString())
            editor.putString("plan_id", UserContainer.user!!.plan_id.toString())
            editor.putString("is_vip", UserContainer.user!!.is_vip.toString())
            editor.apply()
            editor.commit()
            if (loadingFragment != null && loadingFragment!!.showsDialog)
                loadingFragment?.dismiss()

        }
        userSettingViewModel.uploadAvatarLiveData.observe(this) {
            userSettingViewModel.updateUserInformation(
                UpdateUserInformation(
                    this.binding.ActivityEditProfileFirstNameTI.text.toString(),
                    this.binding.ActivityEditProfileLastNameTI.text.toString(),
                    this.binding.ActivityEditProfileEmailTI.text.toString()
                )
            )

        }
    }

    private fun setListener() {
        this.binding.ActivityEditProfileBackBtn.setOnClickListener {
            this.onBackPressed()
        }



        this.binding.ActivityEditProfileEditProfileBtn.setOnClickListener {
            ImagePicker.Companion.with(this)
                .crop()
                .maxResultSize(1080, 1080)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog { launcher.launch(it) }
        }

        this.binding.ActivityEditProfileUpdateBtn.setOnClickListener {
            loadingFragment = LoadingFragment()
            loadingFragment?.show(this.supportFragmentManager, null)
            Handler(mainLooper)
                .postDelayed({
                    if (loadingFragment != null && loadingFragment!!.showsDialog)
                        loadingFragment!!.dismiss()
                }, (30000))
            if (!userProfilePath.isNullOrEmpty())
                userSettingViewModel.uploadAvatarProfile(File(userProfilePath))
            else
                userSettingViewModel.updateUserInformation(
                    UpdateUserInformation(
                        this.binding.ActivityEditProfileFirstNameTI.text.toString(),
                        this.binding.ActivityEditProfileLastNameTI.text.toString(),
                        this.binding.ActivityEditProfileEmailTI.text.toString()
                    )
                )

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
        userProfilePath = path
    }

    override fun PickiTonMultipleCompleteListener(
        paths: ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        TODO("Not yet implemented")
    }
}