package com.example.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.navigation.dynamicfeatures.Constants
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userModel: UserModel)

    @Delete
    fun delete(userModel: UserModel)

    @Delete
    fun deleteAll(list: List<UserModel>)

    @Query("UPDATE user_table SET first_name=:fistName WHERE id=:id")
    fun update(fistName: String, id: Int)

    @Query("SELECT * FROM user_table")
    fun readAllData(): LiveData<List<UserModel>>
}