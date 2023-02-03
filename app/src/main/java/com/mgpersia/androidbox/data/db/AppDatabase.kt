package com.mgpersia.androidbox.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mgpersia.androidbox.data.model.LocalFilmDownload
import com.mgpersia.androidbox.data.source.local.LocalLocalDataSource

@Database(entities = [LocalFilmDownload::class] , version = 4, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun apexDao(): LocalLocalDataSource
}