package com.kyser.mynotes.repository

import com.kyser.mynotes.cache.CacheMapper
import com.kyser.mynotes.cache.NoteCacheDao
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.service.NetworkMapper
import com.kyser.mynotes.service.NotesService
import com.kyser.mynotes.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val cacheDao: NoteCacheDao,
    private val notesService: NotesService,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
){
    suspend fun getNotes():Flow<DataState<List<Note>>> = flow {
        emit(DataState.Loading)

        try {
            val networkNote = notesService.getAllNotes()
            val notes = networkMapper.mapFromEntityList(networkNote)
            for(note in notes){
                cacheDao.insert(cacheMapper.mapToEntity(note))
            }
            val cachedNotes = cacheDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNotes)))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun addNote( name :String , date:String ,priority:String ,body:String):Flow<DataState<List<Note>>> = flow {
        notesService.addNewNote(name,date,priority ,body)
        val networkNote = notesService.getAllNotes()
        val notes = networkMapper.mapFromEntityList(networkNote)
        for(note in notes){
            cacheDao.insert(cacheMapper.mapToEntity(note))
        }
        val cachedNotes = cacheDao.get()
        emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNotes)))
    }
    suspend fun updateNote(note: Note):Flow<DataState<List<Note>>> = flow {
        notesService.updateNote(note.id,note.name,note.body)
        cacheDao.update(cacheMapper.mapToEntity(note))
        val cachedNotes = cacheDao.get()
        emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNotes)))
    }
    suspend fun deleteNote(note: Note):Flow<DataState<List<Note>>> = flow {
        notesService.deleteNote(note.id)
        cacheDao.delete(cacheMapper.mapToEntity(note))
        val cachedNotes = cacheDao.get()
        emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNotes)))
    }
    suspend fun updateNotePriority(note: Note):Flow<DataState<List<Note>>> = flow {
        notesService.updateNotePriority(note.id,note.priority)
        cacheDao.update(cacheMapper.mapToEntity(note))
        val cachedNotes = cacheDao.get()
        emit(DataState.Success(cacheMapper.mapFromEntityList(cachedNotes)))
    }
}