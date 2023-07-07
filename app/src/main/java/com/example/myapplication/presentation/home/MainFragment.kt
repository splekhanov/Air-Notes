package com.example.myapplication.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.data.repo.NoteRepo
import com.example.myapplication.databinding.FragmentMainBinding
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

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

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

        collectNotes()
        mock()

        // FAB CREATE NOTE FRAGMENT
//        binding.fabCreateNoteBtn.setOnClickListener {
//            replaceFragment(CreateNoteFragment.newInstance(), true)
//        }
//
//        binding.searchView.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                viewModel.onSearchQueryChanged(p0.toString())
//                return true
//            }
//        })
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

    private fun mock() {

    }

    private fun setupRecyclerView(){
        binding.rvNoteList.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=noteAdapter
        }

    }
}