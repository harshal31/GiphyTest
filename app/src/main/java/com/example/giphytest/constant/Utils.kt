package com.example.giphytest.constant

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.giphytest.R
import com.example.giphytest.sealedAndenums.Anim
import com.google.android.material.snackbar.Snackbar

fun View.snack(data: Any, length: Int = Snackbar.LENGTH_SHORT) {
    if (isShown && isVisible) {
        if (data is String) {
            Snackbar.make(this, data, length).show()
        } else if (data is Int) {
            Snackbar.make(this, data, length).show()
        }
    }
}

fun Context.toast(data: String) {
    Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
}

internal fun setFragment(
        fragment: Fragment,
        fragmentManager: androidx.fragment.app.FragmentManager,
        container: Int,
        addToBackStack: Boolean = false,
        anim: Anim = Anim.SLIDE,
        isAdd: Boolean = false
) {

    val transaction = fragmentManager.beginTransaction()

    if (addToBackStack)
        transaction.addToBackStack(fragment.javaClass.name)

    when (anim) {
        Anim.FADE -> transaction.setCustomAnimations(
                R.anim.fragment_fade_enter,
                R.anim.fragment_fade_exit,
                R.anim.fragment_fade_enter,
                R.anim.fragment_fade_exit
        )
        Anim.SLIDE -> transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        )
        Anim.OPEN_CLOSE -> transaction.setCustomAnimations(
                R.anim.fragment_open_enter,
                R.anim.fragment_close_enter,
                R.anim.fragment_open_enter,
                R.anim.fragment_close_enter
        )
        Anim.ENTER_EXIT -> transaction.setCustomAnimations(
                R.anim.fragment_open_exit,
                R.anim.fragment_close_exit,
                R.anim.fragment_open_exit,
                R.anim.fragment_close_exit
        )
        Anim.NONE -> logd("no animation")
    }
    if (isAdd) {
        transaction.add(container, fragment).commit()
    } else {
        transaction.replace(container, fragment).commit()
    }
}

fun logd(s: String) = Log.d("commonTag", s);

