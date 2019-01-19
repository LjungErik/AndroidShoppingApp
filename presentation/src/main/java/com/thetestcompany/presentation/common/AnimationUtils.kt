package com.thetestcompany.presentation.common

import android.annotation.TargetApi
import android.os.Build
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils

class AnimationUtils {
    companion object {

        fun registerCircularAnimationReveal(view: View, circularRevealSettings: CircularRevealSettings) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.addOnLayoutChangeListener(object: View.OnLayoutChangeListener {

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun onLayoutChange(v: View, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int, p7: Int, p8: Int) {
                        v.removeOnLayoutChangeListener(this)
                        val cX = circularRevealSettings.centerX
                        val cY = circularRevealSettings.centerY
                        val width = circularRevealSettings.width
                        val height = circularRevealSettings.height

                        val finalRadius: Float = Math.sqrt((width * width + height * height).toDouble()).toFloat()
                        val anim = ViewAnimationUtils.createCircularReveal(v, cX, cY, 0.0f, finalRadius)
                        anim.duration = circularRevealSettings.duration
                        anim.interpolator = FastOutSlowInInterpolator()
                        anim.start()
                    }
                })
            }
        }
    }

}