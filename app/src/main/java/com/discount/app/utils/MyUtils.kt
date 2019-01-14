package com.discount.app.utils

import android.view.Menu
import android.text.Spannable
import android.text.style.TypefaceSpan
import android.text.SpannableString
import android.view.SubMenu



/**
 * Created by Avinash Kumar on 14/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class MyUtils {

    companion object {
        fun addFontToNavDrawer(m: Menu) {
            for (i in 0 until m.size()) {
                val mi = m.getItem(i)

                //for applying a font to subMenu ...
                val subMenu = mi.subMenu
                if (subMenu != null && subMenu.size() > 0) {
                    for (j in 0 until subMenu.size()) {
                        val subMenuItem = subMenu.getItem(j)
                        val s = SpannableString(subMenuItem.title)
                        s.setSpan(
                            TypefaceSpan("font/poppins_light.ttf"), 0, s.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        subMenuItem.title = s
                    }
                }

            }
        }
    }
}