package com.dnkilic.todo.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnkilic.todo.R
import com.dnkilic.todo.core.extension.autoNotify
import com.dnkilic.todo.core.extension.gone
import kotlinx.android.synthetic.main.tag_item.view.*
import kotlin.properties.Delegates

class TagAdapter : RecyclerView.Adapter<TagAdapter.TagItemViewHolder>() {

    private var tags: List<String> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o == n }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TagItemViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.tag_item, parent, false))

    override fun onBindViewHolder(holder: TagItemViewHolder, position: Int) {
        val note = tags[position]
        holder.bind(note)
    }

    override fun getItemCount() = tags.size

    class TagItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(tag: String) {
            if (tag.isNotBlank()) {
                itemView.label.text = tag
            } else {
                itemView.label.gone()
            }
        }
    }

    fun updateList(tags: List<String>) {
        this.tags = tags
    }
}