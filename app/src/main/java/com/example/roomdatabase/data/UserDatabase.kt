package com.example.roomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            var temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}