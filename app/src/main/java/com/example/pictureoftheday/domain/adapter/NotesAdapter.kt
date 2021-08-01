package com.example.pictureoftheday.domain.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureoftheday.R
import com.example.pictureoftheday.domain.repositories.NotesRepository
import com.example.pictureoftheday.domain.repositories.NotesRepositoryImpl
import com.example.pictureoftheday.domain.touchHelper.ItemTouchHelperAdapter
import com.example.pictureoftheday.domain.touchHelper.ItemTouchHelperViewHolder

class NotesAdapter(
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(), ItemTouchHelperAdapter {

    private var repository: NotesRepository = NotesRepositoryImpl()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesAdapter.NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(inflater.inflate(R.layout.fragment_item_note, parent, false))
    }

    override fun onBindViewHolder(holder: NotesAdapter.NotesViewHolder, position: Int) {
        holder.bind(repository.getNotes()[position])
    }

    fun appendNote() {
        repository.addNote(Note("Заметка № ${repository.getSizeNotesList()}"))
        notifyItemInserted(itemCount - 1)
    }



    override fun getItemCount(): Int {
        return repository.getSizeNotesList()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        repository.deleteNote(repository.getNotes()[fromPosition]).apply {
            repository.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)

    }

    override fun onItemDismiss(position: Int) {
        repository.delete(position)
        notifyItemRemoved(position)

    }

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {

        fun bind(note: Note) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.task_view).text = note.someText
                itemView.findViewById<Button>(R.id.btn_remove_note).setOnClickListener {
                    deleteNote(note)
                }
                itemView.setOnClickListener { onListItemClickListener.onItemClick(note) }
            }

        }

        private fun deleteNote(note : Note) {
            repository.deleteNote(note)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {

        }

    }

}

