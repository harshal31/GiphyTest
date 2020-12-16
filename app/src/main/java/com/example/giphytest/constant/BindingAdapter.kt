package com.example.giphytest.constant

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.giphytest.R

object BindingAdapter {

    @BindingAdapter("gifUrl")
    @JvmStatic
    fun ImageView.onGifImageLoading(gifUrl: String?) {
        Glide.with(context)
            .load(gifUrl ?: "")
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(this)
    }

    @BindingAdapter("adapter")
    @JvmStatic
    fun RecyclerView.setAdapter(adapter: RecyclerView.Adapter<*>?) {
        if (adapter?.hasObservers()?.not() == true) {
            adapter.setHasStableIds(true)
        }

        this.adapter = adapter
    }

    @BindingAdapter("isVisible")
    @JvmStatic
    fun View.isViewVisible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("isRefresh")
    @JvmStatic
    fun SwipeRefreshLayout.changeRefreshing(isRefresh: Boolean) {
        isRefreshing = isRefresh
    }

}