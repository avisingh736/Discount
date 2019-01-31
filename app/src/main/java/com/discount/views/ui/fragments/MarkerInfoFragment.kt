package com.discount.views.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.discount.R
import com.discount.app.config.Constants
import com.discount.models.Store
import com.discount.views.ui.activities.StoreDetailActivity

private const val ARG_PARAM = "param"

class MarkerInfoFragment : Fragment() {
    private var store: Store? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            store = it.getSerializable(ARG_PARAM) as Store
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_marker_info, container, false)
        val ivStoreImage = v.findViewById<ImageView>(R.id.ivStoreImage)
        val tvStoreTitle = v.findViewById<TextView>(R.id.tvStoreTitle)
        val tvStoreAddress = v.findViewById<TextView>(R.id.tvStoreAddress)

        Glide.with(context!!).load(store?.storeImage).into(ivStoreImage)
        tvStoreTitle.text = store?.fullName
        tvStoreAddress.text = store?.address
        v.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(Constants.KEY_FROM_WHERE,Constants.KEY_FROM_STORE_LIST)
            mBundle.putSerializable(Constants.KEY_STORE_EXTRA,store)
            val mIntent = Intent(context,StoreDetailActivity::class.java)
            mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,mBundle)
            context?.startActivity(mIntent)
        }
        return v
    }


    companion object {
        @JvmStatic
        fun newInstance(param: Store) =
            MarkerInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM, param)
                }
            }
    }
}
