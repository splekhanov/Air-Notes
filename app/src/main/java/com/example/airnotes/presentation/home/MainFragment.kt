package com.example.airnotes.presentation.home

import android.content.res.ColorStateList
import android.os.Bundle
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
import com.example.airnotes.R
import com.example.airnotes.adapter.NoteAdapter
import com.example.airnotes.data.local.entities.NoteEntity
import com.example.airnotes.data.repo.NoteRepo
import com.example.airnotes.databinding.FragmentMainBinding
import com.example.airnotes.utils.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
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
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kebab: FloatingActionButton = view.findViewById(R.id.kebab)
        val button: FloatingActionButton = view.findViewById(R.id.btnAddNote)

        showBottomSheetDialog(kebab)
        setupRecyclerView()
        collectNotes()
        onClickNote()

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
                    noteAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            binding.rvNoteList.smoothScrollToPosition(0)
                        }
                    })
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
        itemAnimator = FadeInAnimator().apply {
            addDuration = 300
        }
        initSwipeToDeleteNote(this)

        // LISTEN NOTES LIST SCROLL TO CHANGE THE TOOLBAR BACKGROUND IF NOTES ARE UNDER IT
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(recyclerView.canScrollVertically(-1)) {
                    binding.toolbar.setBackgroundResource(R.color.medium_blue)
                    binding.kebab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.medium_blue))
                } else {
                    binding.toolbar.setBackgroundResource(R.color.light_blue)
                    binding.kebab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.light_blue))
                }
            }
        })
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
                viewModel.deleteNoteByID(note.noteId).also {
                    requireActivity().toast(getString(R.string.note_deleted_msg))
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }
    }

    private fun showBottomSheetDialog(kebab:FloatingActionButton) {
        val bottomSheetDialog = ModalBottomSheet()
        kebab.setOnClickListener {
            bottomSheetDialog.show(parentFragmentManager, bottomSheetDialog.tag)
        }
    }
}