package com.example.myapplication.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.data.local.entities.NoteEntity

class NoteDiffUtil(
    private val oldList: List<NoteEntity>,
    private val newList: List<NoteEntity>

) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].noteTitle == newList[newItemPosition].noteTitle
                && oldList[oldItemPosition].noteDescription == newList[newItemPosition].noteDescription
    }
}