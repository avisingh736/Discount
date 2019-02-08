package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.discount.R
import com.discount.adapters.SubscriptionAdapter
import com.discount.app.config.Constants
import com.discount.app.utils.VerticalItemDecoration
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity : AppCompatActivity(),DiscountView {
    override fun onErrorOrInvalid(msg: String) {
        //TODO("not implemented")
    }

    override fun onSuccess(msg: String) {
        //TODO("not implemented")
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
        setContentView(R.layout.activity_subscription)

        rvSubscriptionPlan.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvSubscriptionPlan.addItemDecoration(VerticalItemDecoration())
        rvSubscriptionPlan.adapter = SubscriptionAdapter(this)

        ivGoToBack.setOnClickListener { finish() }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
