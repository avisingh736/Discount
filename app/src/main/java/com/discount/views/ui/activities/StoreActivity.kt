package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.discount.R
import com.discount.presenters.StorePresenter
import com.discount.views.DiscountView
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_store.*


class StoreActivity :DiscountView, AppCompatActivity() {
    private val mPresenter = StorePresenter(this)

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(rootLayoutStore,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(rootLayoutStore,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        //ToDo: Needs implementation
    }

    override fun <T> navigateTo(clazz: Class<T>) {
        startActivity(Intent(this,clazz))
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapSupportFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(mPresenter)

        ivGoToBack.setOnClickListener { finish() }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
