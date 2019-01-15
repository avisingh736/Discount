package com.discount.presenters

import com.discount.views.DiscountView

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomePresenter(var mDiscountView: DiscountView?) {


    fun onDestroy() {
        mDiscountView = null
    }


}