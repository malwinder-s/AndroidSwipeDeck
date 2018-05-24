/*
 * Copyright (C) 2018 Malwinder Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wardrobe.app.controller.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wardrobe.app.R
import com.wardrobe.app.controller.adapter.SwipeDeckAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.view.View
import com.wardrobe.app.controller.adapter.TrouserDeckAdapter
import com.wardrobe.app.controller.swipe.SwipeStack

class MainActivity : AppCompatActivity() {

    private val mShirtAdapter = SwipeDeckAdapter(null, this)
    private val mTrouserAdapter = TrouserDeckAdapter(null, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()

        swipe_deck_shirt.adapter =mShirtAdapter

        swipe_deck_trouser.adapter =mTrouserAdapter

//        val adapter1 = SwipeDeckAdapter(listOf(R.drawable.t0, R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5), this)
//        swipe_deck_trouser.setAdapter(adapter1)
    }

    private fun getBitmapFromAssets(fileName: String): Bitmap {
        val istr = assets.open(fileName)
        return BitmapFactory.decodeStream(istr)
    }

    public fun randomizeClothes(v: View) {
        val random = Random()

        mShirtAdapter.clearData()
        mShirtAdapter.addCloth(getBitmapFromAssets("s"+random.nextInt(5)+".webp"))
        ivShirt.visibility = View.GONE

        mTrouserAdapter.clearData()
        mTrouserAdapter.addCloth(getBitmapFromAssets("t"+random.nextInt(5)+".webp"))
        ivTrouser.visibility = View.GONE
    }

/*
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val builder = ToolTip.Builder(this, tvHint, clRoot, "Welcome!" +
                "\nTap on shirt or jeans to add your own. " +
                "\nSwipe them to remove.\n" +
                "\nClick \uD83D\uDD04 for surprise!", ToolTip.POSITION_ABOVE)
        ToolTipsManager().show(builder.build())
    }*/

    private fun setListeners() {

        swipe_deck_shirt.setListener(object : SwipeStack.SwipeStackListener {
            /**
             * Called when a view has been dismissed to the left.
             *
             * @param position The position of the view in the adapter currently in use.
             */
            override fun onViewSwipedToLeft(position: Int) {
            }

            /**
             * Called when a view has been dismissed to the right.
             *
             * @param position The position of the view in the adapter currently in use.
             */
            override fun onViewSwipedToRight(position: Int) {
            }

            /**
             * Called when the last view has been dismissed.
             */
            override fun onStackEmpty() {
                ivShirt.visibility = View.VISIBLE
            }
        })

        swipe_deck_trouser.setListener(object : SwipeStack.SwipeStackListener {
            /**
             * Called when a view has been dismissed to the left.
             *
             * @param position The position of the view in the adapter currently in use.
             */
            override fun onViewSwipedToLeft(position: Int) {
            }

            /**
             * Called when a view has been dismissed to the right.
             *
             * @param position The position of the view in the adapter currently in use.
             */
            override fun onViewSwipedToRight(position: Int) {
            }

            /**
             * Called when the last view has been dismissed.
             */
            override fun onStackEmpty() {
                ivTrouser.visibility = View.VISIBLE
            }
        })
    }
}