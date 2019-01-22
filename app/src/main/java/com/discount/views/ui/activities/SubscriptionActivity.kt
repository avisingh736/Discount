package com.discount.views.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.discount.R
import com.discount.adapters.SubscriptionAdapter
import com.discount.app.utils.VerticalItemDecoration
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity : AppCompatActivity() {

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
