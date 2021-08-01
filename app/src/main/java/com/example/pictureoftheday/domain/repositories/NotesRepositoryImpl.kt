package com.example.pictureoftheday.domain.repositories

import com.example.pictureoftheday.domain.adapter.Note

class NotesRepositoryImpl : NotesRepository {

    var notesList = mutableListOf<Note>(
        Note("Заметка №1"),
        Note("Заметка №2"),
        Note("Заметка №3"),
        Note("Заметка №4"),
        Note("Заметка №5"),
    )

    var countNotes = 5;


    override fun getNotes(): List<Note> = notesList



    override fun addNote(note: Note) {
        notesList.add(note)
        countNotes.inc()
    }

    override fun add(position: Int, note: Note) {
        notesList.add(position, note)
        countNotes.inc()
    }

    override fun delete(position: Int) {
        if (notesList.size > 0)
        notesList.removeAt(position)
    }

    override fun deleteNote(note: Note): Note {
        if (notesList.size > 0) notesList.remove(note)
        return note
    }

    override fun deleteAllNotes() {
        notesList.clear()
    }

    override fun getSizeNotesList(): Int = notesList.size
}