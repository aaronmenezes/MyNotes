package com.kyser.mynotes.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NotesService {

    @GET("getAllNotes")
    suspend fun getAllNotes(): List<NoteNModel>

    @GET("addNewNote")
    suspend fun addNewNote( @Query("name") name: String, @Query("date") date: String, @Query("priority") priority: String,@Query("body") body: String): List<NoteNModel>

    @GET("updateNote")
    suspend fun  updateNote( @Query("id") id: Int, @Query("name") name: String, @Query("body") body: String ): List<NoteNModel>

    @GET("deleteNote")
    suspend fun deleteNote(@Query("id") id: Int): List<NoteNModel>

    @GET("updateNotePriority")
    suspend fun updateNotePriority( @Query("id") id: Int, @Query("priority") priority: Int ): List<NoteNModel>

}
