package com.example.seoulfestival.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

object AnimationUtils {

    fun fadeIn(view: View, duration: Long, endAction: (() -> Unit)? = null) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    endAction?.invoke()
                }
            })
    }

    fun fadeOut(view: View, duration: Long, endAction: (() -> Unit)? = null) {
        view.animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                    endAction?.invoke()
                }
            })
    }
}
