package com.example.airnotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.airnotes.R
import com.example.airnotes.data.local.entities.NoteEntity
import com.example.airnotes.databinding.ItemNoteBinding
import com.example.airnotes.utils.isToday
import com.example.airnotes.utils.isYesterday
import java.time.format.DateTimeFormatter
import java.util.*

class NoteAdapter (var noteList : List<NoteEntity>) : RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val binding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
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

            val noteDate = item.date
            val locale = Locale.getDefault()
            var today = context.resources.getString(R.string.today_us)
            var yesterday = context.resources.getString(R.string.yesterday_us)

            var formatter = DateTimeFormatter.ofPattern("dd LLL yyyy hh:mm a", locale)
            if(locale.language.equals("ru")) {
                formatter = DateTimeFormatter.ofPattern("dd LLL yyyy HH:mm", locale)
                today = context.resources.getString(R.string.today_ru)
                yesterday = context.resources.getString(R.string.yesterday_ru)
            }

            var formattedDate = noteDate.format(formatter)


            if (isToday(noteDate)) {
                formattedDate = today
            } else if(isYesterday(noteDate)) {
                formattedDate = yesterday
            }

            date.text = formattedDate

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