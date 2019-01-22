package com.discount.customs

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue



/**
 * Created by Avinash Kumar on 22/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class Utils {
    companion object {
        fun dpToPx(dp: Float, context: Context): Int {
            return dpToPx(dp, context.getResources())
        }

        fun dpToPx(dp: Float, resources: Resources): Int {
            val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics())
            return px.toInt()
        }
    }
}