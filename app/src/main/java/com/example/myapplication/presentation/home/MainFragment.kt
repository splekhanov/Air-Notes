package com.example.myapplication.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.data.repo.NoteRepo
import com.example.myapplication.databinding.FragmentMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var noteRepo: NoteRepo

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var note: NoteEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("AAA", "Метод onCreateView класса MainFragment")
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("AAA", "Метод onViewCreated класса MainFragment")

        // SHOW AVAILABLE NOTES USING RV
        collectNotes()
        onClickNote()

        // GO TO CREATE NEW NOTE BY PRESSING NEW NOTE BUTTON
        val button: FloatingActionButton = view.findViewById(R.id.btnAddNote)
        button.setOnClickListener {
            Log.e("AAA", "Нажата кнопка 'Добавить заметку'")
            findNavController().navigate(
                R.id.action_mainFragment_to_addNoteFragment
            )
        }
    }

    private fun onClickNote() {
        // onclick navigate to add notes
        noteAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("note", it)
                Log.e("AAA", "Note: + ${it.noteTitle}")
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_updateNoteFragment,
                bundle
            )
        }
    }

    private fun collectNotes() {
        binding.apply {
            if (noteRepo.getAllNotes().isNotEmpty()) {
                rvNoteList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE
                noteAdapter.differ.submitList(noteRepo.getAllNotes())
                setupRecyclerView()
            } else {
                rvNoteList.visibility = View.GONE
                tvEmptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() = binding.rvNoteList.apply {
        adapter = noteAdapter.apply {
            layoutManager = LinearLayoutManager(activity)
        }

    }
}