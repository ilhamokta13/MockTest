package com.ilham.mocktest.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ilham.mocktest.model.DataToko

@Database(entities = [DataToko::class], version = 1)
abstract class DatabaseToko : RoomDatabase() {
    abstract fun tokoDao() : InterfaceToko

    companion object{
        private var INSTANCE : DatabaseToko? = null

        fun getInstance(context: Context): DatabaseToko?{
            if (INSTANCE == null){
                synchronized(DatabaseToko::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseToko::class.java, "TokoDB").build()
                }
            }
            return  INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}