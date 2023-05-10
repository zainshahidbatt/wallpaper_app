package com.example.mywallpaper.presentation.ext

import android.animation.Animator
import android.animation.ValueAnimator

fun ValueAnimator.addOnEndAction(action: () -> Unit) {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
        }

        override fun onAnimationEnd(animation: Animator) {
            action.invoke()        }

        override fun onAnimationCancel(animation: Animator) {
       }

        override fun onAnimationRepeat(animation: Animator) {
        }
    })
}

inline fun Animator.addEndListener(
    crossinline onEnd: (animator: Animator) -> Unit = {}
): Animator.AnimatorListener {
    val listener = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator) {}
        override fun onAnimationEnd(p0: Animator) = onEnd(p0)
        override fun onAnimationCancel(p0: Animator) {}
        override fun onAnimationStart(p0: Animator) {}
    }
    addListener(listener)
    return listener
}
