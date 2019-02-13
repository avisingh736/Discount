package com.discount.views.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.discount.R
import com.discount.adapters.CouponAdapter
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.EndlessRecyclerViewScrollListener
import com.discount.app.utils.VerticalItemDecoration
import com.discount.interactors.CouponInteractor
import com.discount.models.Category
import com.discount.models.Coupon
import com.discount.presenters.CouponPresenter
import com.discount.views.DiscountView
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : AppCompatActivity(),DiscountView {

    private var mAdapter: CouponAdapter? = null
    private val coupons = mutableListOf<Coupon>()
    private val mPresenter = CouponPresenter(this, CouponInteractor())
    private var offset: Int = 0
    private var couponFilter: String = ""

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        if (clazz == CouponFilterActivity::class.java) {
            startActivityForResult(mIntent,Constants.COUPON_FILTER_REQUEST)
        } else {
            startActivity(mIntent)
        }
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

        val mLinearLayoutManager = LinearLayoutManager(this)
        rvCoupons.layoutManager = mLinearLayoutManager
        rvCoupons.addItemDecoration(VerticalItemDecoration())
        mAdapter = CouponAdapter(this,coupons)
        rvCoupons.adapter = mAdapter
        val scrollListener = object : EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                offset+=10
                mPresenter.getCouponList(offset = offset.toString(),search = couponFilter)
            }
        }
        rvCoupons.addOnScrollListener(scrollListener)

        ivGoToBack.setOnClickListener { finish() }
        ivFilter.setOnClickListener {
            navigateTo(CouponFilterActivity::class.java)
        }

        tvUserAddress.text = Discount.getSession().address
        tvChangeAddress.setOnClickListener {
            val placePicker = PlacePicker.IntentBuilder().build(this@CouponActivity)
            startActivityForResult(placePicker,Constants.PLACE_PICKER_REQUEST)
        }

        val mBundle = intent.getBundleExtra(Constants.KEY_BUNDLE_PARAM)
        if (mBundle != null) {
            if (mBundle.getString(Constants.KEY_FROM_WHERE) == Constants.KEY_FROM_CATEGORY) {
                val category = mBundle.getSerializable(Constants.KEY_CATEGORY_EXTRA) as Category
                couponFilter = category.categoryName
                ivFilter.visibility = View.INVISIBLE
                ivFilter.isClickable = false
            }
        }

        mPresenter.getCouponList(offset = offset.toString(),search = couponFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this@CouponActivity,data)
                tvUserAddress.text = place.address
                offset = 0
                mPresenter.getCouponList(latitude = place.latLng.latitude.toString(),longitude = place.latLng.longitude.toString(),search = couponFilter)
            }
        } else if (requestCode == Constants.COUPON_FILTER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val selectedCategories = data.getStringExtra(Constants.KEY_RESULT_EXTRA)
                    if (selectedCategories.isNotEmpty()) {
                        couponFilter = selectedCategories
                        mPresenter.getCouponList(search = couponFilter)
                        offset = 0
                    } else {
                        couponFilter = ""
                        offset = 0
                        mPresenter.getCouponList(search = couponFilter)
                    }
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    fun onSuccessCouponList(coupons: MutableList<Coupon>) {
        if (offset == 0) {
            this.coupons.clear()
            this.coupons.addAll(coupons)
            mAdapter?.notifyDataSetChanged()
        } else if (offset > 0) {
            this.coupons.addAll(coupons)
            mAdapter?.notifyDataSetChanged()
        }

        if (this.coupons.size == 0) {
            tvNoDataAlert.visibility = View.VISIBLE
        } else {
            tvNoDataAlert.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}
