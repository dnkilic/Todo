package com.dnkilic.todo.detail

import android.os.Bundle
import com.dnkilic.todo.R
import com.dnkilic.todo.core.base.BaseFragment

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

    override fun getLayoutId() = R.layout.detail_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
