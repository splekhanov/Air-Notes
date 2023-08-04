package com.example.airnotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.airnotes.R
import com.example.airnotes.data.local.entities.NoteEntity
import com.example.airnotes.databinding.ItemNoteBinding
import com.example.airnotes.data.local.entities.NoteType
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
        val note = noteList[position]
        holder.binding.apply {

            tvTitle.text = note.title
            if(note.noteType == NoteType.TEXT) {
                tvDesc.text = note.description
            }

            val formattedDate = getFormattedDate(note)
            date.text = formattedDate

            // ON LIST ITEM CLICK
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(note) }
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

    fun getFormattedDate(note : NoteEntity) : String {
        val noteDate = note.date
        val locale = Locale.getDefault()

        // US LOCALE DATE FORMAT BY DEFAULT
        var today = context.resources.getString(R.string.today_us)
        var yesterday = context.resources.getString(R.string.yesterday_us)
        var formatter = DateTimeFormatter.ofPattern("dd LLL yyyy hh:mm a", locale)

        // RU LOCALE FORMAT DATE SUPPORT
        if(locale.language.equals("ru")) {
            formatter = DateTimeFormatter.ofPattern("dd LLL yyyy HH:mm", locale)
            today = context.resources.getString(R.string.today_ru)
            yesterday = context.resources.getString(R.string.yesterday_ru)
        }

        var formattedDate = noteDate.format(formatter)

        // SHOW TODAY OR YESTERDAY INSTEAD OF FULL DATE IF THE NOTE IS FRESH
        if (isToday(noteDate)) {
            formattedDate = today
        } else if(isYesterday(noteDate)) {
            formattedDate = yesterday
        }
        return formattedDate
    }
}