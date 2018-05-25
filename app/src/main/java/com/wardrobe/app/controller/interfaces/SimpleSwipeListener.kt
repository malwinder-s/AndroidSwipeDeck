package com.wardrobe.app.controller.interfaces

import com.wardrobe.app.controller.swipe.SwipeStack

abstract class SimpleSwipeListener : SwipeStack.SwipeStackListener {
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
}