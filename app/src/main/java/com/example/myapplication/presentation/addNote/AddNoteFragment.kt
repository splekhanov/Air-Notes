package com.example.myapplication.presentation.addNote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.data.repo.NoteRepo
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.presentation.home.MainFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    lateinit var binding: FragmentAddNoteBinding

    @Inject
    lateinit var noteRepo: NoteRepo

    @Inject
    lateinit var note: NoteEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("AAA", "Метод onCreateView класса AddNoteFragment")
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("AAA", "Метод onViewCreated класса AddNoteFragment")

        // SAVE NOTE AND GO BACK TO THE MAIN SCREEN
        val saveNoteButton: FloatingActionButton = view.findViewById(R.id.saveNoteButton)
        val editNoteTitle: EditText = view.findViewById(R.id.editTitle)
        val editNoteDescription: EditText = view.findViewById(R.id.editDescription)
        saveNoteButton.setOnClickListener {
            val title = editNoteTitle.text.toString()
            val desc = editNoteDescription.text.toString()
            if (title.isNotEmpty() || desc.isNotEmpty()){
                note = NoteEntity(0,title,desc)
                addNote(note)
                replaceFragment(MainFragment())
                Log.e("AAA", "Заметка сохранена")
            }
            else{
                Snackbar.make(it,"Title and Description cannot be Empty",Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun addNote(note : NoteEntity) = viewLifecycleOwner.lifecycleScope.launch {
        note.apply {
            noteRepo.addNote(note)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.container, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()
    }
}