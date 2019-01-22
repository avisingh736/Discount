package com.discount.views.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.discount.R
import com.discount.adapters.StoreCouponAdapter
import com.discount.app.Discount
import com.discount.app.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_store_detail.*


class StoreDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        ivGoToBack.setOnClickListener { finish() }

        rvCouponAtStore.layoutManager = GridLayoutManager(this,2)
        rvCouponAtStore.addItemDecoration(GridSpacingItemDecoration(2,(Discount.dpToPx(this,10f)),true))
        rvCouponAtStore.adapter = StoreCouponAdapter(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
