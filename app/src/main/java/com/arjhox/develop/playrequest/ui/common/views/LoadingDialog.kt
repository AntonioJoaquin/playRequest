package com.arjhox.develop.playrequest.ui.common.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.arjhox.develop.playrequest.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(
    context: Context
) : Dialog(
    context,
    0
) {

    init {
        setCancelable(false)
        setOnCancelListener(null)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private val fadeInDuration: Long = 200
    private val fadeOutDuration: Long = 200

    private var isHiding: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        constraintLayoutBackground.startAnimation(AlphaAnimation(0f, constraintLayoutBackground.alpha).apply {
            duration = fadeInDuration
        })
    }

    override fun hide() {
        if (!isHiding) {
            isHiding = true
            constraintLayoutBackground.startAnimation(AlphaAnimation(constraintLayoutBackground.alpha, 0f).apply {
                duration = fadeOutDuration
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        super@LoadingDialog.hide()
                        isHiding = false
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }
                })
            })
        }
    }

}