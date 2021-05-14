package com.kyser.mynotes.model.service

import androidx.lifecycle.MutableLiveData
import com.kyser.mynotes.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotesManager() {

    private val BASE_URL = "https://day-notes.herokuapp.com/"

    init {
        initService()
    }

    private object holder{
        val Instance =NotesManager();
    }

    companion object{
        val instance: NotesManager by lazy { holder.Instance }
        private lateinit var mNotesService: NotesService
    }

    fun initService() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mNotesService = retrofit.create(NotesService::class.java)
    }


     fun getAllNotes(notelist: MutableLiveData<List<Note>>) {
        mNotesService.getAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                notelist.value = response.body()
            }
            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun addNewNote( name: String, date: String, priority: String, body: String, notelist: MutableLiveData<List<Note>>) {
        mNotesService.addNewNote(name, date, priority, body).enqueue(object : Callback<List<Note>> {
            override fun onResponse( call: Call<List<Note>>, response: Response<List<Note>>) {
                getAllNotes(notelist)
            }
            override fun onFailure( call: Call<List<Note>>, t: Throwable ) {
                println(t.message)
            }
        })
    }

    fun updateNote(id: Int, name: String, body: String, notelist: MutableLiveData<List<Note>>) {
        mNotesService.updateNote(id, name, body).enqueue(object : Callback<List<Note>> {
            override fun onResponse( call: Call<List<Note>>, response: Response<List<Note>> ) {
                getAllNotes(notelist)
            }
            override fun onFailure( call: Call<List<Note>>, t: Throwable ) {
                println(t.message)
            }
        })
    }

    fun deleteNote(id: Int,notelist: MutableLiveData<List<Note>>) {
        mNotesService.deleteNote(id).enqueue(object : Callback<List<Note>> {
            override fun onResponse( call: Call<List<Note>>, response: Response<List<Note>> ) {
                getAllNotes(notelist)
            }
            override fun onFailure( call: Call<List<Note>>, t: Throwable ) {
                println(t.message)
            }
        })
    }

    fun updateNotePriority(id: Int, priority: Int,notelist: MutableLiveData<List<Note>>) {
        mNotesService.updateNotePriority(id, priority) .enqueue(object : Callback<List<Note>> {
            override fun onResponse( call: Call<List<Note>>, response: Response<List<Note>> ) {
                getAllNotes(notelist)
            }

            override fun onFailure( call: Call<List<Note>>, t: Throwable ) {
                println(t.message)
            }
        })
    }
}