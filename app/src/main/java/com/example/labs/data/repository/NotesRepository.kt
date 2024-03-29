package com.example.labs.data.repository

import androidx.lifecycle.LiveData
import com.example.labs.data.dao.NoteDao
import com.example.labs.data.entities.Note

class NotesRepository(private  val noteDao: NoteDao) {
    val readAllNotes : LiveData<List<Note>> = noteDao.readAllNotes()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}