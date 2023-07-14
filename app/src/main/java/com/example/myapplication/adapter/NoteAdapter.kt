package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.local.entities.NoteEntity
import com.example.myapplication.databinding.ItemNoteBinding
import javax.inject.Inject

class NoteAdapter @Inject constructor() : RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = differ.currentList[position]
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
}