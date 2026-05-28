package com.joydipbhakat.newsapp.data.local.dao

import androidx.room.*
import com.joydipbhakat.newsapp.data.local.entity.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAllArticle(): List<Article>

    @Query("DELETE FROM article")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Article>)

    @Transaction
    fun clearAndInsert(data: List<Article>) {
        deleteAll()
        insertAll(data)
    }
}