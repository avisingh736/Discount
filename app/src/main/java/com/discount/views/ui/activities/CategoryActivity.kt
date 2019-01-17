package com.discount.views.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.discount.R
import com.discount.adapters.CategoryAdapter
import com.discount.app.utils.GridItemDecoration
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        rvCategory.layoutManager = GridLayoutManager(this,2)
        rvCategory.addItemDecoration(GridItemDecoration(2))
        rvCategory.adapter = CategoryAdapter(this)

        ivGoToBack.setOnClickListener { finish() }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
