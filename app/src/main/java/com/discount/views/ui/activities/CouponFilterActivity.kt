package com.discount.views.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.discount.R
import com.discount.adapters.CategoryAdapter
import com.discount.adapters.CouponFilterAdapter
import com.discount.app.Discount
import com.discount.app.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_category.*

class CouponFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_filter)
        rvCategory.layoutManager = GridLayoutManager(this,2)
        rvCategory.addItemDecoration(GridSpacingItemDecoration(2,Discount.dpToPx(this,5f),true))
        rvCategory.adapter = CouponFilterAdapter(this)

        ivGoToBack.setOnClickListener { finish() }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
