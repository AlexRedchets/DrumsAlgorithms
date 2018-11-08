package com.aredchets.drumsalgorithms

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aredchets.drumsalgorithms.menu.MenuAdapter
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * @author Alex Redchets
 */
 
class MenuFragment : Fragment(), MenuAdapter.MenuClickListener {

    private lateinit var adapter : MenuAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menu_recycler_view.layoutManager = GridLayoutManager(context, 1)
        menu_recycler_view.itemAnimator = DefaultItemAnimator()
        adapter = MenuAdapter()
        menu_recycler_view.adapter = adapter
        val menuItems = arrayListOf<String>()
        for (i in 1..15) {
            menuItems.add("ABCDCDAB")
        }
        adapter.updateAdapter(menuItems, this)
    }

    override fun onMenuClicked(name: String) {
        val bundle = Bundle()
        bundle.putString("value", name)
        (activity as MainActivity).navigateWithData(bundle)
    }
}