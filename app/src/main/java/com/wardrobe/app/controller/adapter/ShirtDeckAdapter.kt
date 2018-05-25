package com.wardrobe.app.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wardrobe.app.R

class ShirtDeckAdapter : ClothAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(parent.context)
            view = inflater.inflate(R.layout.shirt, parent, false)
        }

        val iv = view!!.findViewById<ImageView>(R.id.ivCloth)
        iv.setImageBitmap(data[position])
        return view
    }
}