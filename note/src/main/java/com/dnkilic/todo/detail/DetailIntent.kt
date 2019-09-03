package com.dnkilic.todo.detail

import android.content.Context
import android.content.Intent

class DetailIntent : Intent {
    constructor(context: Context, id: Long) : super(context, DetailActivity::class.java) {
        putExtra(EXTRA_ID, id)
        putExtra(EXTRA_MODE, DetailMode.EDIT)
    }

    constructor(context: Context) : super(context, DetailActivity::class.java)

    constructor(intent: Intent): super(intent)

    fun getId(): Long = getLongExtra(EXTRA_ID, -1)

    fun getMode(): DetailMode = getSerializableExtra(EXTRA_MODE) as? DetailMode ?: DetailMode.ADD

    private companion object {
        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_MODE = "EXTRA_MODE"
    }
}

enum class DetailMode {
    ADD,
    EDIT
}