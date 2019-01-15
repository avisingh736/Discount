package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.internal.NavigationMenuView
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.discount.R
import com.discount.adapters.HomeCouponAdapter
import com.discount.app.utils.DividerItemDecoration
import com.discount.app.utils.GridItemDecoration
import com.discount.app.utils.MyUtils
import com.discount.presenters.HomePresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DiscountView {

    private val mHomePresenter = HomePresenter(this);

    override fun onErrorOrInvalid(msg: String) {
        //TODO: To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(msg: String) {
        //TODO: To change body of created functions use File | Settings | File Templates.
    }

    override fun progress(flag: Boolean) {
        //TODO: To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> navigateTo(clazz: Class<T>) {
        //TODO: To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
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

        rvHomeCouponsHere.layoutManager = GridLayoutManager(this,2)
        rvHomeCouponsHere.addItemDecoration(GridItemDecoration(2))
        rvHomeCouponsHere.adapter = HomeCouponAdapter(this)
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
            R.id.nav_coupon -> {
                startActivity(Intent(this,CouponActivity::class.java))
            }
            R.id.nav_category -> {

            }
            R.id.nav_store -> {

            }
            R.id.nav_my_account -> {

            }
            R.id.nav_subscription -> {

            }
            R.id.nav_about_discount -> {

            }
            R.id.nav_contact_us -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        mHomePresenter.onDestroy()
        super.onDestroy()
    }
}
