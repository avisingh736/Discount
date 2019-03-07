package com.discount.views.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.app.config.Constants
import com.discount.models.Coupon
import com.discount.models.CouponInfo
import com.discount.presenters.CouponDetailPresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_coupon_detail.*


class CouponDetailActivity : AppCompatActivity(),DiscountView {
    private val maxLines = 5
    private val twoSpaces = "  "
    private val mPresenter = CouponDetailPresenter(this)
    private var couponInfo: CouponInfo? = null

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(alertRootLayoutCouponDetail,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutCouponDetail,msg, Snackbar.LENGTH_SHORT).show()
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
        setContentView(R.layout.activity_coupon_detail)
        ivGoToBack.setOnClickListener { finish() }
        val bundle = intent.getBundleExtra(Constants.KEY_BUNDLE_PARAM)
        if (bundle != null) {
            val couponId = bundle.getString(Constants.KEY_COUPON_ID_EXTRA)!!
            mPresenter.getCouponInfo(couponId)
        }

        btnRedeem.setOnClickListener {
            if (couponInfo != null) {
                mPresenter.redeemCoupon(couponInfo?.couponId!!,couponInfo?.userId!!)
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    fun onCouponInfo(couponInfo: CouponInfo) {
        this.couponInfo = couponInfo
        Glide.with(this).load(couponInfo.storeImage).into(ivStoreImage)
        Glide.with(this).load(couponInfo.couponImage).into(ivCouponImage)
        tvStoreTitle.text = couponInfo.fullName
        tvStoreAddress.text = couponInfo.address
        tvCouponTitle.text = couponInfo.title
        tvVoucherCode.text = couponInfo.voucherCode
        tvValidDate.text = couponInfo.validDate
        tvTermsAndConditions.text = couponInfo.description
        tvTermsAndConditions.post {
            if (tvTermsAndConditions.lineCount > maxLines) {
                val lastCharShown = tvTermsAndConditions.layout.getLineVisibleEnd(maxLines - 1)

                tvTermsAndConditions.maxLines = maxLines
                tvTermsAndConditions.movementMethod = LinkMovementMethod.getInstance()

                val viewMoreString = getString(R.string.view_more)
                val suffix = twoSpaces + viewMoreString

                // 3 is a "magic number" but it's just basically the length of the ellipsis we're going to insert
                val actionDisplayText = couponInfo.description.substring(0, lastCharShown - suffix.length - 3) + "..." + suffix
                val viewMoreSpan = object : ClickableSpan() {
                    override fun onClick(v: View) {
                        tvTermsAndConditions.maxLines = Int.MAX_VALUE
                        tvTermsAndConditions.invalidate()
                        tvTermsAndConditions.text = couponInfo.description
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

        llStoreLayout.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(Constants.KEY_FROM_WHERE,Constants.KEY_FROM_COUPON_INFO)
            mBundle.putSerializable(Constants.KEY_COUPON_INFO_EXTRA,couponInfo)
        }
    }
}
