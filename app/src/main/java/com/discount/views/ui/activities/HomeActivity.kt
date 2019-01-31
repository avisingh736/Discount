package com.discount.views.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.internal.NavigationMenuView
import android.support.design.widget.AppBarLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.discount.R
import com.discount.adapters.HomeCouponAdapter
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.DividerItemDecoration
import com.discount.app.utils.MyUtils
import com.discount.app.utils.GridSpacingItemDecoration
import com.discount.app.utils.MyLog
import com.discount.interactors.HomeInteractor
import com.discount.models.Coupon
import com.discount.presenters.HomePresenter
import com.discount.views.HomeView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HomeView {
    private val TAG: String = HomeActivity::class.java.simpleName

    private val mHomePresenter = HomePresenter(this, HomeInteractor())
    private var mCouponAdapter: HomeCouponAdapter? = null
    private var coupons = mutableListOf<Coupon>()

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(alertRootLayoutHome,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutHome,msg, Snackbar.LENGTH_SHORT).show()
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
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbarHome)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbarHome, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val navMenuView = nav_view.getChildAt(0) as NavigationMenuView
        val divider = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.horizontal_divider)!!)
        navMenuView.addItemDecoration(divider)
        nav_view.menu.getItem(0).isChecked = true
        MyUtils.addFontToNavDrawer(nav_view.menu)

        appBarHome.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout!!.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarHome.title = "Home                   "
                    isShow = true
                } else if(isShow) {
                    collapsingToolbarHome.title = " "
                    isShow = false
                }
            }
        })
        mHomePresenter.requestForLocationAccess()

        goForCoupon.setOnClickListener { navigateTo(CouponActivity::class.java) }

        rvHomeCouponsHere.layoutManager = GridLayoutManager(this, 2)
        rvHomeCouponsHere.addItemDecoration(GridSpacingItemDecoration(2,Discount.dpToPx(this,5f),true))
        mCouponAdapter = HomeCouponAdapter(this,coupons)
        rvHomeCouponsHere.adapter =  mCouponAdapter

        Log.d(TAG,"StudentId: ${Discount.getSession().studentId} ${Discount.getSession().studentId.isEmpty()}")
        if (Discount.getSession().studentId.isEmpty())
            mHomePresenter.studentDialogStatus()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_coupon -> navigateTo(CouponActivity::class.java)
            R.id.nav_category -> navigateTo(CategoryActivity::class.java)
            R.id.nav_store -> navigateTo(StoreActivity::class.java)
            R.id.nav_my_account -> navigateTo(ProfileActivity::class.java)
            R.id.nav_subscription -> navigateTo(SubscriptionActivity::class.java)
            R.id.nav_about_discount -> navigateTo(AboutUsActivity::class.java)
            R.id.nav_contact_us -> navigateTo(ContactUsActivity::class.java)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mHomePresenter.getLastKnownLocation()
            } else {
                mHomePresenter.requestForLocationAccess()
            }
        }
    }

    override fun onCouponSuccess(coupons: MutableList<Coupon>) {
        this.coupons.clear()
        this.coupons.addAll(coupons)
        mCouponAdapter?.notifyDataSetChanged()
        MyLog.i(TAG,"Coupon list size : ${coupons.size}")
    }

    override fun onDestroy() {
        mHomePresenter.onDestroy()
        super.onDestroy()
    }
}
