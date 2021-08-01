package com.example.pictureoftheday.domain.repositories

import com.example.pictureoftheday.domain.adapter.Note

interface NotesRepository {
    fun getNotes(): List<Note>
    fun addNote(note: Note)
    fun add(position: Int, note: Note)
    fun delete(position: Int)
    fun deleteNote(note: Note) : Note
    fun deleteAllNotes()
    fun getSizeNotesList(): Int
}