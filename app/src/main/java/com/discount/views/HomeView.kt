package com.discount.views

import com.discount.models.Coupon

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
interface HomeView: DiscountView {
    fun onCouponSuccess(coupons: MutableList<Coupon>)
}