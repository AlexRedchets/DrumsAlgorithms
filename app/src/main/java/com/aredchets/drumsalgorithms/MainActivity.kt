package com.aredchets.drumsalgorithms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.aredchets.drumsalgorithms.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var bundle: Bundle? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_menu -> {
                navigateToFragment(MenuFragment(), false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val fragment = DashboardFragment()
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                navigateToFragment(fragment, false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_metronome -> {
                navigateToFragment(MetronomeFragment(), false)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setNavigationTo(R.id.navigation_menu)
    }

    fun navigateWithData(bundle : Bundle) {
        this.bundle = bundle
        navigation.selectedItemId = R.id.navigation_dashboard
    }

    fun setNavigationTo(id : Int) {
        navigation.selectedItemId = id
    }
}