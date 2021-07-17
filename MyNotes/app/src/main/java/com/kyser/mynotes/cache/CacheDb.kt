package com.kyser.mynotes.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteCacheModel::class], version = 1,exportSchema = false)
abstract class CacheDb: RoomDatabase() {

    abstract fun cacheDao() : NoteCacheDao;

    companion object{
        val DATABASE_NAME:String ="note_db"
    }
}