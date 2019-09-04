package com.dnkilic.todo.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.dnkilic.todo.R
import com.dnkilic.todo.core.extension.autoNotify
import com.dnkilic.todo.core.extension.gone
import com.dnkilic.todo.core.extension.visible
import com.dnkilic.todo.data.json.Note
import kotlinx.android.synthetic.main.dashboard_item.view.*
import kotlin.properties.Delegates

class DashboardAdapter(private val clickListener: (Long) -> Unit)
    : RecyclerView.Adapter<DashboardAdapter.DashboardItemViewHolder>() {

    var tracker: SelectionTracker<Long>? = null

    private var notes: List<Note> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DashboardItemViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.dashboard_item, parent, false), clickListener)

    override fun onBindViewHolder(holder: DashboardItemViewHolder, position: Int) {
        val note = notes[position]
        tracker?.let {
            holder.bind(note, it.isSelected(note.id))
        }
    }

    override fun getItemCount() = notes.size

    override fun getItemId(position: Int) = notes[position].id

    class DashboardItemViewHolder(
        itemView: View,
        private val clickListener: (Long) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note, isActivated: Boolean = false) = with(itemView) {
            itemView.setOnClickListener { clickListener.invoke(note.id) }
            if (note.title.isNotBlank()) {
                itemView.title.text = note.title
            } else {
                itemView.title.gone()
            }

            if (note.description.isNotBlank()) {
                itemView.description.text = note.description
            } else {
                itemView.description.gone()
            }

            itemView.isActivated = isActivated
            if (note.tags.isNotEmpty() && note.tags.first().isNotBlank()) {
                itemView.label.text = note.tags.first()
                itemView.label.visible()
                if (note.tags.size > 1) {
                    itemView.labelCount.text = "+${note.tags.size - 1}"
                    itemView.labelCount.visible()
                } else {
                    itemView.labelCount.gone()
                }
            } else {
                itemView.label.gone()
                itemView.labelCount.gone()
            }

            if (note.isCompleted) {
                itemView.completed.visible()
            } else {
                itemView.completed.gone()
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }

    fun updateList(notes: List<Note>) {
        this.notes = notes
    }
}