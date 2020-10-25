package com.example.cartrack.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cartrack.entity.AppUser
import com.example.cartrack.dao.AppUserDao

@Database(entities = [AppUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appUserDao(): AppUserDao

}