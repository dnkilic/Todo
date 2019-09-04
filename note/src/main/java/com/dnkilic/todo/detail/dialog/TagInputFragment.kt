package com.dnkilic.todo.detail.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.dnkilic.todo.R

class TagInputFragment : DialogFragment() {

    private val viewModel: SharedTagViewModel by lazy {
        ViewModelProviders.of(activity!!).get(SharedTagViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.tag_input_dialog, view as ViewGroup?, false)
            val input = view.findViewById(R.id.input) as EditText
            AlertDialog.Builder(context!!)
                .setView(view)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    val tag = input.text.toString()
                    if (tag.isNotBlank()) {
                        val value = viewModel.tags.value
                        val tags = value?.also { it.add(tag) } ?: mutableListOf(tag)
                        viewModel.tags.postValue(tags)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}