package com.example.newsapp.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.storage.news.NewsDao
import com.example.newsapp.storage.news.NewsEntity
import com.example.newsapp.storage.remotekey.RemoteKeysDao
import com.example.newsapp.storage.remotekey.RemoteKeysEntity


@Database(entities = [NewsEntity::class, RemoteKeysEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "name")
                .build()
        }
    }
}