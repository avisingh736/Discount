package com.discount.views.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.discount.R
import com.discount.adapters.CouponAdapter
import com.discount.app.utils.VerticalItemDecoration
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)

        rvCoupons.layoutManager = LinearLayoutManager(this)
        rvCoupons.addItemDecoration(VerticalItemDecoration())
        rvCoupons.adapter = CouponAdapter(this)
    }
}
