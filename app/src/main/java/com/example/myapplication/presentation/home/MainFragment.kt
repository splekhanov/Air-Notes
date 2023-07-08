package com.example.myapplication.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.data.repo.NoteRepo
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.presentation.addNote.AddNoteFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var  repository: NoteRepo

    @Inject
    lateinit var noteAdapter: NoteAdapter


    @Inject
    lateinit var note: NoteEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("AAA", "onViewCreated works")
        var note = NoteEntity(0,
            "Важная заметка 2",
            "Встретить бабушку в аэропорту")
        repository.addNote(note)

        // SHOW AVAILABLE NOTES USING RV
        collectNotes()

        // CREATE NOTE FRAGMENT BY PRESSING NEW NOTE BUTTON
        val button: FloatingActionButton = view.findViewById(R.id.btnAddNote)
        button.setOnClickListener {
            Log.e("AAA", "AddNewNoteButton pressed")
            replaceFragment(AddNoteFragment())
        }
    }

    private fun collectNotes(){
        binding.apply {
            if(repository.getAllNotes().isNotEmpty()){
                rvNoteList.visibility= View.VISIBLE
                tvEmptyText.visibility=View.GONE
                noteAdapter.differ.submitList(repository.getAllNotes())
                setupRecyclerView()
            }else{
                rvNoteList.visibility=View.GONE
                tvEmptyText.visibility=View.VISIBLE
            }
        }
    }

    // OPEN NEW NOTE SCREEN
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.container, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()
    }

    private fun setupRecyclerView(){
        binding.rvNoteList.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=noteAdapter
        }

    }
}