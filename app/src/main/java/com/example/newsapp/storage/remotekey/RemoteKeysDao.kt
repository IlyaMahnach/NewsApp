package com.example.newsapp.storage.remotekey

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE id = :articleId")
    suspend fun getRemoteKeys(articleId: String): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()

}