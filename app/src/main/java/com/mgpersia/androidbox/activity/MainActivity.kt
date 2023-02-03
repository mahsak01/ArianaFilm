package com.mgpersia.androidbox.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mgpersia.androidbox.Fragment.*
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.model.User
import com.mgpersia.androidbox.viewModel.LocalShareViewModel
import workManager
import org.koin.androidx.viewmodel.ext.android.viewModel

import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>

    private val localShareViewModel: LocalShareViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workManager = WorkManager.getInstance(this)
        requestMultiplePermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            var isGranted = false
            it.forEach { (s, b) ->
                isGranted = b
            }

            if (!isGranted) {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.activityMain_navHostContainer
        ) as NavHostFragment
        navController = navHostFragment.navController


        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {

                R.id.home -> {
                    selectedFragment = HomeFragment()
                }
                R.id.serial -> {
                    selectedFragment = AllSerialFragment()
                }
                R.id.film -> {
                    selectedFragment = AllFilmFragment()
                }
                R.id.category -> {
                    selectedFragment = CategoryFragment()
                }
                R.id.live -> {
                    selectedFragment = LiveFragment()
                }
            }
            if (selectedFragment != null)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.activityMain_navHostContainer, selectedFragment)
                    .commit()
            true
        }
        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home, R.id.serial, R.id.category, R.id.film, R.id.live)
        )


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()
        getData()

    }

    override fun onDestroy() {
        super.onDestroy()
        exitProcess(0)
    }

    private fun getData() {
        val preferences: SharedPreferences = this.getSharedPreferences("", Context.MODE_PRIVATE)
        if (preferences.getString("token", null) != null) {
            TokenContainer.update(preferences.getString("token", null))
            UserContainer.update(
                User(
                    preferences.getString("avatar", null),
                    preferences.getString("email", null),
                    preferences.getString("first_name", null),
                    preferences.getString("is_vip", null).toBoolean(),
                    preferences.getString("last_name", null),
                    preferences.getString("phone", null),
                    preferences.getString("plan_id", null)
                )
            )

            if (preferences.getString("is_iran", null) != null) {
                UserContainer.is_iran = preferences.getString("is_iran", null).toBoolean()
            }

            localShareViewModel.getAllFilm()

        }

    }
}