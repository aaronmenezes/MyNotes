package com.kyser.mynotes.model.service

import com.kyser.mynotes.model.Note
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NotesService {

    @GET("getAllNotes")
    fun getAllNotes(): Call<List<Note>>

    @GET("addNewNote")
    fun addNewNote( @Query("name") name: String, @Query("date") date: String, @Query("priority") priority: String,@Query("body") body: String): Call<List<Note>>

    @GET("updateNote")
    fun updateNote( @Query("id") id: Int, @Query("name") name: String, @Query("body") body: String ): Call<List<Note>>

    @GET("deleteNote")
    fun deleteNote(@Query("id") id: Int): Call<List<Note>>

    @GET("updateNotePriority")
    fun updateNotePriority( @Query("id") id: Int, @Query("priority") priority: Int ): Call<List<Note>>

}