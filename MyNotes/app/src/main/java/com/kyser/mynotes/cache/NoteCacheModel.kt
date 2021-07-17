package com.kyser.mynotes.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class NoteCacheModel (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="id")
    var id :Int,

    @ColumnInfo(name = "key")
    var key:Int,

    @ColumnInfo(name = "priority")
    var priority:Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "body")
    var body: String,

    @ColumnInfo(name = "date")
    var date: String
){}