package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.adapters.StoreCouponAdapter
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.GridSpacingItemDecoration
import com.discount.models.Coupon
import com.discount.models.CouponInfo
import com.discount.models.Store
import com.discount.presenters.StoreDetailPresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_store_detail.*


class StoreDetailActivity : AppCompatActivity(), DiscountView {
    private val mPresenter = StoreDetailPresenter(this)
    private var mAdapter: StoreCouponAdapter? = null
    private val coupons = mutableListOf<Coupon>()

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(alertRootLayoutStoreDetail,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutStoreDetail,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        progressBar.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)
        ivGoToBack.setOnClickListener { finish() }

        val mBundle = intent.getBundleExtra(Constants.KEY_BUNDLE_PARAM)
        if (mBundle != null) {
            if (mBundle.getString(Constants.KEY_FROM_WHERE) == Constants.KEY_FROM_COUPON_INFO) {
                val couponInfo = mBundle.getSerializable(Constants.KEY_COUPON_INFO_EXTRA) as CouponInfo
                //ToDo: Manage if intent from coupon info
            } else if (mBundle.getString(Constants.KEY_FROM_WHERE) == Constants.KEY_FROM_STORE_LIST) {
                val store = mBundle.getSerializable(Constants.KEY_STORE_EXTRA) as Store
                mPresenter.getStoreInfo(store.storeId)
            }
        }

        rvCouponAtStore.layoutManager = GridLayoutManager(this,2)
        rvCouponAtStore.addItemDecoration(GridSpacingItemDecoration(2,(Discount.dpToPx(this,10f)),true))
        mAdapter = StoreCouponAdapter(this,coupons)
        rvCouponAtStore.adapter = mAdapter
    }

    fun onStoreInfo(storeInfo: Store, coupons: MutableList<Coupon>) {
        Glide.with(this).load(storeInfo.storeImage).into(ivStoreImage)
        tvStoreTitle.text = storeInfo.fullName
        tvStoreAddress.text = storeInfo.address

        this.coupons.clear()
        this.coupons.addAll(coupons)
        mAdapter?.notifyDataSetChanged()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
