package com.kyser.mynotes.cache

import com.kyser.mynotes.model.Note
import com.kyser.mynotes.service.NoteNModel
import com.kyser.mynotes.util.EntityMapper
import javax.inject.Inject

class CacheMapper

@Inject
constructor():EntityMapper<NoteCacheModel,Note>{
    override fun mapFromEntity(entity: NoteCacheModel): Note {
        return Note(
            id=entity.id,
            key=entity.key,
            priority = entity.priority,
            name= entity.name,
            body = entity.body,
            date = entity.date)
    }

    override fun mapToEntity(domainModel: Note): NoteCacheModel {
        return NoteCacheModel(
            id=domainModel.id,
            key=domainModel.key,
            priority = domainModel.priority,
            name= domainModel.name,
            body = domainModel.body,
            date = domainModel.date)
    }

    fun mapFromEntityList(entities : List<NoteCacheModel>):List<Note>{
        return entities.map{mapFromEntity(it)}
    }
}