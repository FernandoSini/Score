package com.flemis.score.features.app.data.datasource.local.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.flemis.score.core.utils.ConvertersRoom
import com.flemis.score.features.app.data.datasource.local.db.dao.UserDao
import com.flemis.score.features.app.data.models.UserModel

@Database(entities = [UserModel::class], version = 1)
@TypeConverters(ConvertersRoom::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    //abstract fun getTheme(): ThemeRepository
    // abstract fun deleteDatabase(): Boolean

}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}


/*fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        //.addMigrations(MIGRATIONS)
        .fallbackToDestructiveMigrationOnDowngrade(false)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}*/


expect class AppDatabaseBuilder {
    fun getRoomDatabase(): AppDatabase
    fun deleteDatabase(): Boolean

}