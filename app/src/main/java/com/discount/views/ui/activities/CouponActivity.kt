package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.discount.R
import com.discount.adapters.CouponAdapter
import com.discount.app.config.Constants
import com.discount.app.utils.VerticalItemDecoration
import com.discount.views.BaseView
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : AppCompatActivity(),BaseView {
    override fun <T> navigateTo(clazz: Class<T>) {
        startActivity(Intent(this,clazz))
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)

        rvCoupons.layoutManager = LinearLayoutManager(this)
        rvCoupons.addItemDecoration(VerticalItemDecoration())
        rvCoupons.adapter = CouponAdapter(this)

        ivGoToBack.setOnClickListener { finish() }
        ivFilter.setOnClickListener {
            navigateTo(CouponFilterActivity::class.java)
        }

        tvChangeAddress.setOnClickListener {
            val placePicker = PlacePicker.IntentBuilder().build(this@CouponActivity)
            startActivityForResult(placePicker,Constants.PLACE_PICKER_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(this@CouponActivity,data)
                tvAddress.text = place.address
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
