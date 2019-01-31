package com.discount.presenters

import com.discount.app.Discount
import com.discount.interactors.CouponInteractor
import com.discount.models.Coupon
import com.discount.views.DiscountView
import com.discount.views.ui.activities.CouponActivity

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CouponPresenter(var mDiscountView: DiscountView?, var mInteractor: CouponInteractor): CouponInteractor.OnResponseListener {
    override fun onSuccess(msg: String) {
        mDiscountView?.onSuccess(msg)
    }

    override fun progress(flag: Boolean) {
        mDiscountView?.progress(flag)
    }

    override fun onError(msg: String) {
        mDiscountView?.onErrorOrInvalid(msg)
    }

    fun onDestroy() {
        mDiscountView = null
    }

    fun getCouponList(latitude: String = Discount.getSession().latitude, longitude: String = Discount.getSession().longitude, search: String = "", limit: String = "10", offset: String = "0") {
        mInteractor.getCouponListFromServer(latitude,longitude,search,limit,offset,this)
    }

    override fun onSuccessCouponList(coupons: MutableList<Coupon>) {
        (mDiscountView as CouponActivity).onSuccessCouponList(coupons)
    }
}