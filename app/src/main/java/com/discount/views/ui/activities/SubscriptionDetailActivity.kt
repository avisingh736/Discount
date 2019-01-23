package com.discount.views.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.discount.R
import kotlinx.android.synthetic.main.activity_subscription_detail.*

class SubscriptionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription_detail)

        ivGoToBack.setOnClickListener { finish() }
    }
}
