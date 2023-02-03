package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.NotificationItemSettingAdapter
import com.mgpersia.androidbox.adapter.SwipeToSeenCallback
import com.mgpersia.androidbox.common.MainContainer
import com.mgpersia.androidbox.data.model.Notification
import com.mgpersia.androidbox.databinding.ActivityNotificationBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private val userSettingViewModel: UserSettingViewModel by viewModel()
    private var loadingFragment: LoadingFragment? = null
    private var listItemSeen = ArrayList<Notification>()
    private var listItemUnSeen = ArrayList<Notification>()
    private var seen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
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
        userSettingViewModel.getAllNotification()
        setObserver()
        setListener()

    }

    private fun setListener() {
        this.binding.ActivityNotificationBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.ActivityNotificationItemRG.setOnCheckedChangeListener { _, _ ->
            MainContainer.seen = !MainContainer.seen
            setInformation()
        }
    }

    private fun clickNotification(notification: Notification) {
        startActivity(
            Intent(
                this,
                FilmDetailActivity::class.java
            ).apply {
                putExtra("id_film", notification.id.toString())
            })
    }

    private fun setInformation() {
        val list = ArrayList<Notification>()
        if (this.binding.ActivityNotificationAllItemSeenRB.isChecked) {

            list.clear()
            list.addAll(listItemSeen.toList())

        } else if (this.binding.ActivityNotificationAllItemUnSeenRB.isChecked) {
            list.clear()
            list.addAll(listItemUnSeen.toList())

        }
        if (list.isNotEmpty()) {
            this.binding.ActivityNotificationEmptyLayout.root.visibility = View.GONE
            this.binding.ActivityNotificationNotificationRv.visibility = View.VISIBLE
//            this.binding.ActivityNotificationReadAllBtn.visibility = View.VISIBLE
            binding.ActivityNotificationNotificationRv.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            val adapter =
                NotificationItemSettingAdapter(list as ArrayList<Notification>,
                    object : NotificationItemSettingAdapter.EvenListener {
                        override fun click(notification: Notification) {

//                        if (notification.status != "مشاهده شده") {
//                            listItemUnSeen.remove(notification)
//                            listItemSeen.add(notification)
//                            userSettingViewModel.updateNotifications(notification.id.toString())
//                            setInformation()
//                        }
                            //TODO
//                        clickNotification(notification)
                        }

                    })


            val swipeHandler = object : SwipeToSeenCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (MainContainer.seen){
                        val item = listItemSeen[viewHolder.adapterPosition]
                        listItemSeen.remove(item)
                        item.status ="مشاهده نشده"
                        listItemUnSeen.add(item)
                        userSettingViewModel.updateNotifications(item.id.toString(), "u")
                        setInformation()
                    }else {
                        val item = listItemUnSeen[viewHolder.adapterPosition]
                        listItemUnSeen.remove(item)
                        item.status ="مشاهده شده"
                        listItemSeen.add(item)
                        userSettingViewModel.updateNotifications(item.id.toString(), "s")
                        setInformation()
                    }
                }

            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this.binding.ActivityNotificationNotificationRv)


            binding.ActivityNotificationNotificationRv.adapter = adapter
            if (loadingFragment != null && loadingFragment!!.showsDialog)
                loadingFragment!!.dismiss()
        } else {
            this.binding.ActivityNotificationEmptyLayout.root.visibility = View.VISIBLE
            this.binding.ActivityNotificationNotificationRv.visibility = View.GONE
            this.binding.ActivityNotificationReadAllBtn.visibility = View.GONE
            if (loadingFragment != null && loadingFragment!!.showsDialog)
                loadingFragment!!.dismiss()

        }

    }

    private fun setObserver() {
        userSettingViewModel.allNotificationsLiveData.observe(this) {
            listItemSeen.clear()
            listItemUnSeen.clear()
            if (it.results!!.isNotEmpty()) {
                for (item in it.results) {
                    if (item!!.status == "مشاهده شده")
                        listItemSeen.add(item)
                    else
                        listItemUnSeen.add(item)
                }

            }
            setInformation()
        }
    }
}