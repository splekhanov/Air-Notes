package com.example.myapplication.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.data.repo.NoteRepo
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.BaseItemAnimator
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: NoteViewModel by activityViewModels()
    lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var noteRepo: NoteRepo

    private val noteAdapter by lazy { NoteAdapter(arrayListOf()) }

    @Inject
    lateinit var note: NoteEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        collectNotes()
        onClickNote()

        // GO TO CREATE NEW NOTE BY PRESSING NEW NOTE BUTTON
        val button: FloatingActionButton = view.findViewById(R.id.btnAddNote)
        button.setOnClickListener {
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
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_updateNoteFragment,
                bundle
            )
        }
    }

    private fun collectNotes() {
        binding.apply {
            viewModel.getAllData.observe(viewLifecycleOwner, Observer {
                if(it.isNotEmpty()) {
                    rvNoteList.visibility = View.VISIBLE
                    tvEmptyText.visibility = View.GONE
                    noteAdapter.setList(it)
                } else {
                    rvNoteList.visibility = View.GONE
                    tvEmptyText.visibility = View.VISIBLE
                }

            })
        }
    }

    private fun setupRecyclerView() = binding.rvNoteList.apply {
        adapter = noteAdapter
        layoutManager = LinearLayoutManager(activity)
        itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        initSwipeToDeleteNote(this)
    }

    private fun initSwipeToDeleteNote(recyclerView: RecyclerView) {
        // init item touch callback for swipe action
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get item position & delete notes
                val position = viewHolder.adapterPosition
                val note = noteAdapter.noteList[position]
                viewModel.deleteNoteByID(note.id).also {
                    requireActivity().toast(getString(R.string.note_deleted_msg))
                }
            }
        }
        // attach swipe callback to rv
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }
    }
}