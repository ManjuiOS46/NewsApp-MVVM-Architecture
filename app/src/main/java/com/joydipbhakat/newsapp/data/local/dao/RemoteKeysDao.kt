package com.joydipbhakat.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joydipbhakat.newsapp.data.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        remoteKeys: List<RemoteKeys>
    )

    @Query(
        "SELECT * FROM remote_keys WHERE articleUrl = :articleUrl"
    )
    suspend fun getRemoteKey(
        articleUrl: String
    ): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}