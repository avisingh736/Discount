package com.discount.views.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.discount.R
import com.discount.views.ui.activities.StoreDetailActivity

private const val ARG_PARAM = "param"

class BlankFragment : Fragment() {
    private var param: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_marker_info, container, false)
        v.setOnClickListener {
            context?.startActivity(Intent(context,StoreDetailActivity::class.java))
        }
        return v
    }


    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}
