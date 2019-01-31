package com.discount.presenters

import android.content.Context
import android.util.Log
import com.appolica.interactiveinfowindow.InfoWindow
import com.appolica.interactiveinfowindow.InfoWindowManager
import com.discount.R
import com.discount.app.Discount
import com.discount.app.utils.MyLog
import com.discount.interactors.StoreInteractor
import com.discount.models.Store
import com.discount.views.DiscountView
import com.discount.views.ui.fragments.MarkerInfoFragment
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
class StorePresenter(var mDiscountView: DiscountView?,var infoWindowManager: InfoWindowManager?,  var mInteractor: StoreInteractor):
    OnMapReadyCallback,GoogleMap.OnMarkerClickListener,InfoWindowManager.WindowShowListener,
    StoreInteractor.OnResponseListener {
    private val TAG = StorePresenter::class.java.simpleName
    private var mMap: GoogleMap? = null
    private val infoWindows = mutableListOf<InfoWindow>()
    private val markers = mutableListOf<Marker>()
    private val offsetX: Int
    private val offsetY: Int
    private val markerSpec: InfoWindow.MarkerSpecification

    init {
        offsetX = (mDiscountView as Context).resources.getDimension(R.dimen.marker_offset_x).toInt()
        offsetY = (mDiscountView as Context).resources.getDimension(R.dimen.marker_offset_y).toInt()
        markerSpec = InfoWindow.MarkerSpecification(offsetX, offsetY)
    }

    override fun onSuccessStoreList(stores: MutableList<Store>) {
        for (store in stores) {
            val mMarker = mMap?.addMarker(MarkerOptions().position(LatLng(store.latitude.toDouble(),store.longitude.toDouble())).snippet(store.storeId))!!
            markers.add(mMarker)
            infoWindows.add(InfoWindow(mMarker,markerSpec,MarkerInfoFragment.newInstance(store)))
        }
    }

    override fun onSuccess(msg: String) {
        mDiscountView?.onSuccess(msg)
    }

    override fun progress(flag: Boolean) {
        mDiscountView?.progress(flag)
    }

    override fun onError(msg: String) {
        mDiscountView?.onErrorOrInvalid(msg)
    }

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

    override fun onMarkerClick(marker: Marker?): Boolean {
        /*mHomeView?.navigateTo(StoreDetailActivity::class.java)*/
        for ((i, myMarker) in markers.withIndex()) {
            Log.d(TAG,"$i > ${myMarker.snippet} == ${marker?.snippet}")
            if (myMarker.snippet == marker?.snippet) {
                infoWindowManager?.toggle(infoWindows[i],true)
                return false
            }
        }

        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.run {
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(Discount.getSession().latitude.toDouble(),Discount.getSession().longitude.toDouble()),16f))
            setOnMarkerClickListener(this@StorePresenter)
            mMap = this
        }
    }

    fun onDestroy() {
        mDiscountView = null
    }

    fun getStoreList() {
        mInteractor.getStoreListFromServer(this)
    }
}