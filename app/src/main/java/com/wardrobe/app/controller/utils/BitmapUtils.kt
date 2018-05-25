package com.wardrobe.app.controller.utils

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

class BitmapUtils(val assets: AssetManager) {

    fun getBitmapFromAssets(fileName: String): Bitmap {
        val istr = assets.open(fileName)
        return getResizedBitmap(BitmapFactory.decodeStream(istr))
    }

    private fun getResizedBitmap(bm: Bitmap): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = 200.toFloat() / width
        val scaleHeight = 200.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false)
        bm.recycle()
        return resizedBitmap
    }
}