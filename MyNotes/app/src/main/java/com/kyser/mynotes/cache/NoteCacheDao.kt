package com.kyser.mynotes.cache

import androidx.room.*

@Dao
interface NoteCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteCacheModel: NoteCacheModel):Long

    @Delete()
    suspend fun delete(noteCacheModel: NoteCacheModel):Int

    @Query("SELECT * from notes")
    suspend fun get():List<NoteCacheModel>

    @Update
    suspend fun update(note:NoteCacheModel):Int
}