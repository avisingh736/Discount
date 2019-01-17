package com.discount.views.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.discount.R
import kotlinx.android.synthetic.main.activity_coupon_detail.*


class CouponDetailActivity : AppCompatActivity() {
    private val maxLines = 5
    private val twoSpaces = "  "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_detail)

        ivGoToBack.setOnClickListener { finish() }

        tvTermsAndConditions.post {
            if (tvTermsAndConditions.lineCount > maxLines) {
                val lastCharShown = tvTermsAndConditions.layout.getLineVisibleEnd(maxLines - 1)

                tvTermsAndConditions.maxLines = maxLines
                tvTermsAndConditions.movementMethod = LinkMovementMethod.getInstance()

                val viewMoreString = getString(R.string.view_more)
                val suffix = twoSpaces + viewMoreString

                // 3 is a "magic number" but it's just basically the length of the ellipsis we're going to insert
                val actionDisplayText =
                    getString(R.string.l4).substring(0, lastCharShown - suffix.length - 3) + "..." + suffix
                val viewMoreSpan = object : ClickableSpan() {
                    override fun onClick(v: View) {
                        tvTermsAndConditions.maxLines = Int.MAX_VALUE
                        tvTermsAndConditions.invalidate()
                        tvTermsAndConditions.text = getString(R.string.l4)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = Color.RED
                    }
                }

                val truncatedSpannableString = SpannableString(actionDisplayText)
                val startIndex = actionDisplayText.indexOf(viewMoreString)
                truncatedSpannableString.setSpan(
                    viewMoreSpan,
                    startIndex,
                    startIndex + viewMoreString.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                tvTermsAndConditions.text = truncatedSpannableString
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
