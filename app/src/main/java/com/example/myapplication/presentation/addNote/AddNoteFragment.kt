package com.example.myapplication.presentation.addNote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.presentation.home.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    lateinit var binding: FragmentAddNoteBinding

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initViews()
//        collectNotes()
//    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}