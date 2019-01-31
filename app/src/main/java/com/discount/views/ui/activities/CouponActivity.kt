package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.discount.R
import com.discount.adapters.CouponAdapter
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.VerticalItemDecoration
import com.discount.models.Coupon
import com.discount.views.DiscountView
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : AppCompatActivity(),DiscountView {

    private var mAdapter: CouponAdapter? = null
    private val coupons = mutableListOf<Coupon>()

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(alertRootLayoutCoupon,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutCoupon,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        progressBar.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)

        rvCoupons.layoutManager = LinearLayoutManager(this)
        rvCoupons.addItemDecoration(VerticalItemDecoration())
        mAdapter = CouponAdapter(this,coupons)
        rvCoupons.adapter = mAdapter

        ivGoToBack.setOnClickListener { finish() }
        ivFilter.setOnClickListener {
            navigateTo(CouponFilterActivity::class.java)
        }

        tvUserAddress.text = Discount.getSession().address
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
                tvUserAddress.text = place.address
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    fun onSuccessCouponList(coupons: MutableList<Coupon>) {

    }
}
