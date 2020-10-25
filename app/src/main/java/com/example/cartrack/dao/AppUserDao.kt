package com.example.cartrack.dao

import androidx.room.*
import com.example.cartrack.entity.AppUser

@Dao
interface AppUserDao {

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    fun findByUserNameAndPassword(email: String, password: String): AppUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users_AppUser: AppUser): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(users_AppUser: AppUser)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

}