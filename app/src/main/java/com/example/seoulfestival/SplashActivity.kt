package com.example.seoulfestival

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.seoulfestival.choice.ChoiceActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView = findViewById<ImageView>(R.id.splashImageView)

        Glide.with(this)
            .load(R.drawable.splash_base_img)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    imageView.setImageDrawable(resource)
                    animateImageView(imageView, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun animateImageView(imageView: ImageView, drawable: Drawable) {
        val screenWidth = imageView.width
        val screenHeight = imageView.height
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        val scaleX = screenWidth.toFloat() / drawableWidth.toFloat()
        val scaleY = screenHeight.toFloat() / drawableHeight.toFloat()
        val scale = maxOf(scaleX, scaleY)

        val translateX = (screenWidth - drawableWidth * scale) / 2
        val translateY = (screenHeight - drawableHeight * scale) / 2

        val matrix = Matrix().apply {
            postScale(scale, scale)
            postTranslate(translateX, translateY)
        }
        imageView.scaleType = ImageView.ScaleType.MATRIX
        imageView.imageMatrix = matrix

        val startTransX = 0f
        val endTransX = -(drawableWidth.toFloat() * scale - screenWidth) / 2

        val animator = ValueAnimator.ofFloat(startTransX, endTransX).apply {
            addUpdateListener { animation ->
                val transX = animation.animatedValue as Float
                val newMatrix = Matrix(matrix).apply {
                    postTranslate(transX, 0f)
                }
                imageView.imageMatrix = newMatrix
            }
            interpolator = LinearInterpolator()
            duration = 2000
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    val preferences: SharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                    val firstRun = preferences.getBoolean("firstRun", true)

                    val intent = if (firstRun) {
                        Intent(this@SplashActivity, ChoiceActivity::class.java)
                    } else {
                        Intent(this@SplashActivity, MainActivity::class.java)
                    }
                    startActivity(intent)
                    finish()
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        animator.start()
    }
}
