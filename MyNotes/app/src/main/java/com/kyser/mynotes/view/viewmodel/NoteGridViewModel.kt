package com.kyser.mynotes.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.model.service.NotesManager
import kotlinx.coroutines.launch

class NoteGridViewModel() : ViewModel() {

    private var noteList: MutableLiveData<List<Note>> = MutableLiveData()

    fun getNoteList(): MutableLiveData<List<Note>> {
        NotesManager.instance.getAllNotes(noteList)
        return noteList
    }
}