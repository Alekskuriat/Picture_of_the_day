package com.example.pictureoftheday.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.ToDoListFragmentBinding
import com.example.pictureoftheday.domain.adapter.Note
import com.example.pictureoftheday.domain.adapter.NotesAdapter
import com.example.pictureoftheday.domain.adapter.OnListItemClickListener
import com.example.pictureoftheday.domain.touchHelper.ItemTouchHelperCallback
import com.example.pictureoftheday.domain.viewBinding

class ToDoListFragment : Fragment(R.layout.to_do_list_fragment) {

    private val notesListRecycler: RecyclerView? = null
    private var adapter: NotesAdapter? = null

    companion object {
        fun newInstance() = ToDoListFragment()
    }

    private val B: ToDoListFragmentBinding by viewBinding(
        ToDoListFragmentBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = NotesAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(note: Note) {
                    Toast.makeText(context, note.someText, Toast.LENGTH_SHORT).show()
                }
            }
        )
        B.recyclerView.adapter = adapter
        B.addNote.setOnClickListener { adapter.let {
            it?.appendNote()
        } }

        adapter.let {
            ItemTouchHelper(ItemTouchHelperCallback(it!!))
                .attachToRecyclerView(B.recyclerView)
        }



    }


}