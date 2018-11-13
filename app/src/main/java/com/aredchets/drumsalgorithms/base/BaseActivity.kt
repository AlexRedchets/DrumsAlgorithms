package com.aredchets.drumsalgorithms.base

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.aredchets.drumsalgorithms.R

/**
 * @author Alex Redchets
 */
 
abstract class BaseActivity : AppCompatActivity() {

    fun navigateToFragment(fragment: Fragment, addToBackStack : Boolean) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                .commit()
        if (addToBackStack) {
            supportFragmentManager.beginTransaction().addToBackStack("").commit()
        }
    }
}