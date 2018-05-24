package com.wardrobe.app.controller.tooltips;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import com.wardrobe.app.R;

class ToolTipBackgroundConstructor {

    /**
     * Select which background will be assign to the tip view
     */
    static void setBackground(View tipView, ToolTip toolTip) {

        // show tool tip without arrow. no need to continue
        if (toolTip.hideArrow()) {
            setToolTipNoArrowBackground(tipView, toolTip.getBackgroundColor());
            return;
        }

        // show tool tip according to requested position
        switch (toolTip.getPosition()) {
            case ToolTip.POSITION_ABOVE:
                setToolTipAboveBackground(tipView, toolTip);
                break;
            case ToolTip.POSITION_BELOW:
                setToolTipBelowBackground(tipView, toolTip);
                break;
            case ToolTip.POSITION_LEFT_TO:
                setToolTipLeftToBackground(tipView, toolTip.getBackgroundColor());
                break;
            case ToolTip.POSITION_RIGHT_TO:
                setToolTipRightToBackground(tipView, toolTip.getBackgroundColor());
                break;
        }

    }

    private static void setToolTipAboveBackground(View tipView, ToolTip toolTip) {
        switch (toolTip.getAlign()) {
            case ToolTip.ALIGN_CENTER:
                setTipBackground(tipView, R.drawable.tooltip_arrow_down, toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_LEFT:
                setTipBackground(tipView,
                        !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_down_left :
                                R.drawable.tooltip_arrow_down_right
                        , toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_RIGHT:
                setTipBackground(tipView,
                        !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_down_right :
                                R.drawable.tooltip_arrow_down_left
                        , toolTip.getBackgroundColor());
                break;
        }
    }

    private static void setToolTipBelowBackground(View tipView, ToolTip toolTip) {

        switch (toolTip.getAlign()) {
            case ToolTip.ALIGN_CENTER:
                setTipBackground(tipView, R.drawable.tooltip_arrow_up, toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_LEFT:
                setTipBackground(tipView,
                        !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_up_left :
                                R.drawable.tooltip_arrow_up_right
                        , toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_RIGHT:
                setTipBackground(tipView,
                        !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_up_right :
                                R.drawable.tooltip_arrow_up_left
                        , toolTip.getBackgroundColor());
                break;
        }

    }

    private static void setToolTipLeftToBackground(View tipView, int color) {
        setTipBackground(tipView, !UiUtils.isRtl() ?
                        R.drawable.tooltip_arrow_right : R.drawable.tooltip_arrow_left,
                color);
    }

    private static void setToolTipRightToBackground(View tipView, int color) {
        setTipBackground(tipView, !UiUtils.isRtl() ?
                        R.drawable.tooltip_arrow_left : R.drawable.tooltip_arrow_right,
                color);
    }

    private static void setToolTipNoArrowBackground(View tipView, int color) {
        setTipBackground(tipView, R.drawable.tooltip_no_arrow, color);
    }

    private static void setTipBackground(View tipView, int drawableRes, int color){
        Drawable paintedDrawable = getTintedDrawable(tipView.getContext(),
                drawableRes, color);
        setViewBackground(tipView, paintedDrawable);
    }

    private static void setViewBackground(View view, Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private static Drawable getTintedDrawable(Context context, int drawableRes, int color){
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(drawableRes, null);
            if (drawable != null) {
                drawable.setTint(color);
            }
        } else {
            drawable = context.getResources().getDrawable(drawableRes);
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }

        return drawable;
    }

}
