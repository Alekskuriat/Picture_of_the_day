package com.example.pictureoftheday.ui.main

import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.PictureOfTheDayFragmentStartBinding
import com.example.pictureoftheday.databinding.ToDoListFragmentBinding
import com.example.pictureoftheday.domain.viewBinding

class ToDoListFragment : Fragment(R.layout.to_do_list_fragment) {
    companion object {
        fun newInstance() = ToDoListFragment()
    }

    private val B: ToDoListFragmentBinding by viewBinding(
        ToDoListFragmentBinding::bind
    )


}