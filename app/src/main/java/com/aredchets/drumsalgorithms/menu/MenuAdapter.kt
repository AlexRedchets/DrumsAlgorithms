package com.aredchets.drumsalgorithms.menu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aredchets.drumsalgorithms.R
import kotlinx.android.synthetic.main.item_menu.view.*

/**
 * @author Alex Redchets
 */

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private lateinit var menuList : List<String>
    private lateinit var clickListener: MenuClickListener

    fun updateAdapter(menuList : List<String>, clickListener: MenuClickListener) {
        this.menuList = menuList
        this.clickListener = clickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.item_menu, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.name.text = menuList[p1]
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val name: TextView = view.item_name

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            clickListener.onMenuClicked(menuList[adapterPosition])
        }
    }

    interface MenuClickListener {
        fun onMenuClicked(name : String)
    }
}
 