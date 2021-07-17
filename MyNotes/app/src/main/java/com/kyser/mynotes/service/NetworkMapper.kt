package com.kyser.mynotes.service

import com.kyser.mynotes.model.Note
import javax.inject.Inject
import com.kyser.mynotes.util.EntityMapper

class NetworkMapper
    @Inject
    constructor():EntityMapper<NoteNModel, Note>{
    override fun mapFromEntity(entity: NoteNModel): Note {
       return Note(
           id=entity.id,
           key=entity.key,
           priority = entity.priority,
           name= entity.name,
           body = entity.body,
           date = entity.date)
    }

    override fun mapToEntity(domainModel: Note): NoteNModel {
        return NoteNModel(
            id=domainModel.id,
            key=domainModel.key,
            priority = domainModel.priority,
            name= domainModel.name,
            body = domainModel.body,
            date = domainModel.date)
    }

    fun mapFromEntityList(entities : List<NoteNModel>):List<Note>{
        return entities.map{mapFromEntity(it)}
    }
}
