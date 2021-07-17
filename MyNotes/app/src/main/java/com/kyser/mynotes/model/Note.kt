package com.kyser.mynotes.model

import java.io.Serializable

data class Note(
     var id :Int,
     var key:Int ,
     var priority:Int,
     var name: String,
     var body: String,
     var date: String) : Serializable