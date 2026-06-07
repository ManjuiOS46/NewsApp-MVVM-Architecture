package com.joydipbhakat.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joydipbhakat.newsapp.data.local.dao.ArticleDao
import com.joydipbhakat.newsapp.data.local.dao.RemoteKeysDao
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.local.entity.RemoteKeys

@Database(entities = [Article::class, RemoteKeys::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
     abstract fun articleDao(): ArticleDao
     abstract fun remoteKeysDao() : RemoteKeysDao
}