package com.flemis.score.features.app.data.datasource.local.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual class AppDatabaseBuilder {
    private val ctx: Context;

    constructor(ctx: Context) {
        this.ctx = ctx
    }

    actual fun getRoomDatabase(): AppDatabase {
        val appContext = ctx.applicationContext
        val dbFile = appContext.getDatabasePath("score.db")
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        ) //.addMigrations(MIGRATIONS)
            .fallbackToDestructiveMigrationOnDowngrade(false)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()

    }

    actual fun deleteDatabase(): Boolean {
        val appContext = ctx.applicationContext
        return appContext.deleteDatabase("score.db")
    }


}