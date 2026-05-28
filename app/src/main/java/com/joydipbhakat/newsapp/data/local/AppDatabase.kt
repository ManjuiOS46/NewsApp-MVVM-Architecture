package com.joydipbhakat.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joydipbhakat.newsapp.data.local.dao.ArticleDao
import com.joydipbhakat.newsapp.data.local.entity.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
     abstract fun articleDao(): ArticleDao
}