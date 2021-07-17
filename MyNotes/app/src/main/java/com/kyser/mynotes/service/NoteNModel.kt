package com.kyser.mynotes.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoteNModel (

    @SerializedName(value = "id")
    @Expose
    var id :Int,

    @SerializedName(value = "key")
    @Expose
    var key:Int ,

    @SerializedName(value = "priority")
    @Expose
    var priority:Int,

    @SerializedName(value = "name")
    @Expose
    var name: String,

    @SerializedName(value = "body")
    @Expose
    var body: String,

    @SerializedName(value = "date")
    @Expose
    var date: String
){}