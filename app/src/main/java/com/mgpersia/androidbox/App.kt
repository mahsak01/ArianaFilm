package com.mgpersia.androidbox

import android.app.Application
import androidx.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.mgpersia.androidbox.data.db.AppDatabase
import com.mgpersia.androidbox.data.implement.LocalRepositoryImplement
import com.mgpersia.androidbox.data.implement.MainRepositoryImplement
import com.mgpersia.androidbox.data.implement.UserRepositoryImplement
import com.mgpersia.androidbox.data.repository.LocalRepository
import com.mgpersia.androidbox.data.repository.MainRepository
import com.mgpersia.androidbox.data.repository.UserRepository
import com.mgpersia.androidbox.data.source.remote.MainRemoteDataSource
import com.mgpersia.androidbox.data.source.remote.UserRemoteDataSource
import com.mgpersia.androidbox.service.FrescoLoadingServiceImplement
import com.mgpersia.androidbox.service.ImageLoadingService
import com.mgpersia.androidbox.service.http.createApiServiceInstance
import com.mgpersia.androidbox.viewModel.LocalShareViewModel
import com.mgpersia.androidbox.viewModel.MainViewModel
import com.mgpersia.androidbox.viewModel.SharedViewModel
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single {
                Room.databaseBuilder(this@App, AppDatabase::class.java, "ariana_download_db_app")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }

            single<ImageLoadingService> { FrescoLoadingServiceImplement() }

            factory { createApiServiceInstance() }
            factory<LocalRepository> {
                LocalRepositoryImplement(get<AppDatabase>().apexDao())
            }
            factory<UserRepository> {
                UserRepositoryImplement(UserRemoteDataSource(get()))
            }
            factory<MainRepository> {
                MainRepositoryImplement(MainRemoteDataSource(get()))
            }
            single { LocalShareViewModel(get()) }
            single { SharedViewModel(get()) }
            viewModel { UserSettingViewModel(get()) }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}