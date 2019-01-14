package com.discount.views.ui.activities

import android.os.Bundle
import android.support.design.internal.NavigationMenuView
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.discount.R
import com.discount.app.utils.DividerItemDecoration
import com.discount.app.utils.MyUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        MyUtils.addFontToNavDrawer(nav_view.menu)
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
}
