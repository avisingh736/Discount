package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.discount.R
import com.discount.adapters.SubscriptionAdapter
import com.discount.app.config.Constants
import com.discount.app.utils.VerticalItemDecoration
import com.discount.models.Subscription
import com.discount.presenters.SubscriptionPresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity : AppCompatActivity(),DiscountView {
    private var plans = mutableListOf<Subscription>()
    private var mAdapter: SubscriptionAdapter? = null
    private val mPresenter = SubscriptionPresenter(this)

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(subscriptionActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(subscriptionActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
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
        mAdapter = SubscriptionAdapter(this,plans)
        rvSubscriptionPlan.adapter = mAdapter

        ivGoToBack.setOnClickListener { finish() }
        mPresenter.getSubscriptionList()
    }

    fun onSuccessPlanList(plans: MutableList<Subscription>) {
        this.plans.clear()
        this.plans.addAll(plans)
        mAdapter?.notifyDataSetChanged()

        if (this.plans.size == 0) {
            tvNoDataAlert.visibility = View.VISIBLE
        } else {
            tvNoDataAlert.visibility = View.GONE
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}
