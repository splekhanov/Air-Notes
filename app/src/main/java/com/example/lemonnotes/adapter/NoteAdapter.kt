package com.example.lemonnotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lemonnotes.data.local.entities.NoteEntity
import com.example.lemonnotes.databinding.ItemNoteBinding

class NoteAdapter (var noteList : List<NoteEntity>) : RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = noteList[position]
        holder.binding.apply {

            tvTitle.text = item.noteTitle
            tvDesc.text = item.noteDescription

            // on item click
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    inner class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    // on item click listener
    private var onItemClickListener: ((NoteEntity) -> Unit)? = null
    fun setOnItemClickListener(listener: (NoteEntity) -> Unit) {
        onItemClickListener = listener
    }

    fun setList(list: List<NoteEntity>) {
        val diffUtil = NoteDiffUtil(noteList, list)
        val differResult = DiffUtil.calculateDiff(diffUtil)
        this.noteList = list
        differResult.dispatchUpdatesTo(this)
    }
}