package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.appolica.interactiveinfowindow.InfoWindowManager
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.interactors.StoreInteractor
import com.discount.presenters.StorePresenter
import com.discount.views.DiscountView
import com.google.android.gms.location.places.AutocompleteFilter
import kotlinx.android.synthetic.main.activity_store.*


class StoreActivity :DiscountView, AppCompatActivity() {
    private var mPresenter: StorePresenter? = null
    private var infoWindowManager: InfoWindowManager? = null

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(rootLayoutStore,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(rootLayoutStore,msg, Snackbar.LENGTH_SHORT).show()
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
        setContentView(R.layout.activity_store)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.infoWindowMap) as MapInfoWindowFragment?
        infoWindowManager = mapFragment?.infoWindowManager()
        infoWindowManager?.setHideOnFling(true)
        mPresenter = StorePresenter(this,infoWindowManager, StoreInteractor())
        mapFragment?.getMapAsync(mPresenter)
        infoWindowManager?.setWindowShowListener(mPresenter)
        ivGoToBack.setOnClickListener { finish() }
        tvPlaceAddress.text = Discount.getSession().address

        mPresenter?.getStoreList()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

}
