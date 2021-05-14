package com.kyser.mynotes.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.model.service.NotesManager

class NoteGridViewModel() : ViewModel() {

    private var noteList: MutableLiveData<List<Note>> = MutableLiveData()

    init{
        NotesManager.instance.getAllNotes(noteList)
    }

    fun getNoteList(): MutableLiveData<List<Note>> {
        return noteList
    }

    fun updateNotePriority(id: Int, priority: Int) {
        NotesManager.instance.updateNotePriority(id,priority,noteList)
    }

    fun deleteNote(id: Int){
        NotesManager.instance.deleteNote(id,noteList)
    }

    fun updateNote(id: Int, name: String, body: String){
        NotesManager.instance.updateNote(  id,name,body ,noteList)
    }

    fun addNewNote( name: String, date: String, priority: String, body: String ){
        NotesManager.instance.addNewNote(  name,  date, "1", body ,noteList)
    }
}