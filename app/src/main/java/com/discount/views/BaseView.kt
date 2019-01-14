package com.discount.views

/**
 * Created by Avinash Kumar on 14/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
interface BaseView {
    fun <T> navigateTo(clazz: Class<T>)
}