package com.wardrobe.app.controller.adapter

import android.graphics.Bitmap
import android.widget.BaseAdapter

abstract class ClothAdapter : BaseAdapter() {
    public var data = ArrayList<Bitmap>()

    override fun getCount(): Int {
        return data.size
    }

    fun addClothAndGetData(bmp: Bitmap): ArrayList<Bitmap> {
        data.add(0, bmp)
        notifyDataSetChanged()
        return data
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