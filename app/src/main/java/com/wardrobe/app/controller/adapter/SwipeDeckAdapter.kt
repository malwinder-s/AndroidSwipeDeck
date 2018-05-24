package com.wardrobe.app.controller.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wardrobe.app.R

class SwipeDeckAdapter(private var data: ArrayList<Int>?, private val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return data?.size ?: 0
    }

    fun addCloth(id: Int) {
        if (data==null) {
            data = ArrayList()
        }
        data!!.add(id)
        notifyDataSetChanged()
    }

    fun clearData() {
        data = null
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return data!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            // normally use a viewholder
            view = inflater.inflate(R.layout.cloth, parent, false)
        }

        val iv = view!!.findViewById<ImageView>(R.id.ivCloth)

        Picasso.get().load(data!![position]).fit().centerCrop().into(iv)
        return view
    }
}