package com.kyser.mynotes.view.viewmodel

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.repository.MainRepository
import com.kyser.mynotes.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
   @Inject
    constructor(
        val mainRepository: MainRepository,
        @Assisted private val savedStateHandle: SavedStateHandle):ViewModel(){
            private val _dataState :MutableLiveData<DataState<List<Note>>> = MutableLiveData()

            val dataState:LiveData<DataState<List<Note>>>
                    get ()=_dataState

            fun setStateEvent(mainStateEvent: Any){
                viewModelScope.launch {

                    when(mainStateEvent){
                        is MainStateEvent.GetNotesEvent->{
                            mainRepository.getNotes().onEach {
                                dataState -> _dataState.value=dataState;
                            }.launchIn(viewModelScope)
                        }
                        is MainStateEvent.None -> {  }
                    }
                }
            }

            fun setStateEvent(
                mainStateEvent: Any,
                name: String,
                date: String,
                priority: String,
                body: String
            ) {
                viewModelScope.launch {
                    when (mainStateEvent) {
                        is MainStateEvent.AddNotesEvent -> {
                            mainRepository.addNote(name,date,priority,body).onEach {
                                    dataState -> _dataState.value=dataState;
                            }.launchIn(viewModelScope)
                        }
                        is MainStateEvent.None -> {
                        }
                    }
                }
            }

        fun setStateEvent(
            mainStateEvent: Any,
            note: Note,
        ) {
            viewModelScope.launch {
                when (mainStateEvent) {
                    is MainStateEvent.UpdateNotesEvent -> {
                        mainRepository.updateNote(note).onEach {
                                dataState -> _dataState.value=dataState;
                        }.launchIn(viewModelScope)
                    }
                    is MainStateEvent.DeleteNoteEvent -> {
                        mainRepository.deleteNote(note).onEach {
                                dataState -> _dataState.value=dataState;
                        }.launchIn(viewModelScope)
                    }
                    is MainStateEvent.UpdateNotesPriorityEvent -> {
                        mainRepository.updateNotePriority(note).onEach {
                                dataState -> _dataState.value=dataState;
                        }.launchIn(viewModelScope)
                    }
                    is MainStateEvent.None -> {
                    }
                }
            }
        }
}

sealed class MainStateEvent{
    object GetNotesEvent:MainStateEvent()
    object None:MainStateEvent()
    object AddNotesEvent:MainStateEvent()
    object UpdateNotesEvent:MainStateEvent()
    object DeleteNoteEvent:MainStateEvent()
    object UpdateNotesPriorityEvent:MainStateEvent()
}