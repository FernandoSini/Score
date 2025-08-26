package com.flemis.score.features.app.data.datasource.local.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

actual class AppDatabaseBuilder {
    constructor()
    actual fun getRoomDatabase(): AppDatabase {
        val dbFilePath = documentDirectory() + "/score.db"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
            //     factory =  { AppDatabase::class.instantiateImpl() }
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO).build()

    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun deleteDatabase(): Boolean {
        val appContext = documentDirectory()
        val dbFilePath = "$appContext/score.db"
        val fileManager = NSFileManager.defaultManager
        return fileManager.removeItemAtPath(dbFilePath, error = null)
    }


}

