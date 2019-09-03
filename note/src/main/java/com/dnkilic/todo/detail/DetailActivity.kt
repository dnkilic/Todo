package com.dnkilic.todo.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnkilic.todo.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            val intent = DetailIntent(intent)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance(intent.getMode(), intent.getId()))
                .commitNow()
        }
    }

}
