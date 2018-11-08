package com.aredchets.drumsalgorithms

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * @author Alex Redchets
 */
 
class DashboardFragment : Fragment() {

    private var value : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        value = if (arguments != null) {
            arguments!!.getString("value")
        } else ""
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!TextUtils.isEmpty(value)) {
            Toast.makeText(context, "This is $value", Toast.LENGTH_SHORT).show()
        }
        button_metronom.setOnClickListener {
            (activity as MainActivity).setNavigationTo(R.id.navigation_metronome)
        }
        button2.setOnClickListener {
            Toast.makeText(context, "Button 2 clicked", Toast.LENGTH_SHORT).show()
        }
        button3.setOnClickListener {
            Toast.makeText(context, "Button 3 clicked", Toast.LENGTH_SHORT).show()
        }
    }
}