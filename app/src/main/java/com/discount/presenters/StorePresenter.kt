package com.discount.presenters

import android.content.Context
import com.appolica.interactiveinfowindow.InfoWindow
import com.appolica.interactiveinfowindow.InfoWindowManager
import com.discount.R
import com.discount.app.utils.MyLog
import com.discount.views.DiscountView
import com.discount.views.ui.fragments.BlankFragment
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
class StorePresenter(var mDiscountView: DiscountView?,var infoWindowManager: InfoWindowManager?):
    OnMapReadyCallback,GoogleMap.OnMarkerClickListener,InfoWindowManager.WindowShowListener {
    private val TAG = StorePresenter::class.java.simpleName

    override fun onWindowHideStarted(infoWindow: InfoWindow) {
        MyLog.i(TAG,"onWindowHideStarted")
    }

    override fun onWindowHidden(infoWindow: InfoWindow) {
        MyLog.i(TAG,"onWindowHidden")
    }

    override fun onWindowShown(infoWindow: InfoWindow) {
        MyLog.i(TAG,"onWindowShown")
    }

    override fun onWindowShowStarted(infoWindow: InfoWindow) {
        MyLog.i(TAG,"onWindowShowStarted")
    }

    var mMarker: Marker? = null
    var mInfoWindow: InfoWindow? = null

    override fun onMarkerClick(marker: Marker?): Boolean {
        /*mDiscountView?.navigateTo(StoreDetailActivity::class.java)*/
        infoWindowManager?.toggle(mInfoWindow!!,true)

        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.run {
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.7051,75.9091),16f))
            mMarker = addMarker(MarkerOptions().position(LatLng(22.7051,75.9091)).snippet("Test"))
            setOnMarkerClickListener(this@StorePresenter)
            this
        }

        val offsetX = (mDiscountView as Context).resources.getDimension(R.dimen.marker_offset_x).toInt()
        val offsetY = (mDiscountView as Context).resources.getDimension(R.dimen.marker_offset_y).toInt()
        val markerSpec = InfoWindow.MarkerSpecification(offsetX, offsetY)

        mInfoWindow = InfoWindow(mMarker,markerSpec,BlankFragment.newInstance(""))

    }

    fun onDestroy() {
        mDiscountView = null
    }
}