package com.discount.views

/**
 * Created by Avinash Kumar on 11/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
interface DiscountView: BaseView {
    fun onErrorOrInvalid(msg: String)
    fun onSuccess(msg: String)
    fun progress(flag: Boolean)
}