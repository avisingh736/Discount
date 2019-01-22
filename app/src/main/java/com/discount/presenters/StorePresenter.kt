package com.discount.presenters

import com.discount.views.DiscountView
import com.discount.views.ui.activities.StoreDetailActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Avinash Kumar on 22/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class StorePresenter(var mDiscountView: DiscountView?): OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    override fun onMarkerClick(marker: Marker?): Boolean {
        mDiscountView?.navigateTo(StoreDetailActivity::class.java)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.run {
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.7051,75.9091),16f))
            addMarker(MarkerOptions().position(LatLng(22.7051,75.9091)))
            googleMap.setOnMarkerClickListener(this@StorePresenter)
            this
        }
    }

    fun onDestroy() {
        mDiscountView = null
    }
}