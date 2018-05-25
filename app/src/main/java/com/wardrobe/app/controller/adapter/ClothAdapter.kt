package com.wardrobe.app.controller.adapter

import android.graphics.Bitmap
import android.widget.BaseAdapter

abstract class ClothAdapter : BaseAdapter() {
    protected var data = ArrayList<Bitmap>()

    override fun getCount(): Int {
        return data.size
    }

    fun addCloth(bmp: Bitmap) {
        data.add(bmp)
        data.reverse()
        notifyDataSetChanged()
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}