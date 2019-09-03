package com.dnkilic.todo.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.dnkilic.todo.R
import com.dnkilic.todo.core.base.BaseFragment
import com.dnkilic.todo.detail.viewmodel.DetailViewModel

class DetailFragment : BaseFragment() {

    companion object {
        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_MODE = "EXTRA_MODE"

        fun newInstance(mode: DetailMode, id: Long) = DetailFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_ID, id)
                putSerializable(EXTRA_MODE, mode)
            }
        }
    }

    private lateinit var viewModel: DetailViewModel

    override fun getLayoutId() = R.layout.detail_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

}
