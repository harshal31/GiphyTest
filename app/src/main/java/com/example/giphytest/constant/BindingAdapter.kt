package com.example.giphytest.constant

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.giphytest.R
import com.google.android.material.appbar.MaterialToolbar

object BindingAdapter {

    @BindingAdapter("gifUrl")
    @JvmStatic
    fun ImageView.onGifImageLoading(gifUrl: String?) {
        GlideApp
            .with(context)
            .load(gifUrl)
            .placeholder(R.drawable.ic_baseline_gif_24)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .skipMemoryCache(true)
            .into(this)
    }

    @BindingAdapter("adapter")
    @JvmStatic
    fun RecyclerView.setAdapterOnRecyclerView(adapter: RecyclerView.Adapter<*>?) {
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

    @BindingAdapter("srcData")
    @JvmStatic
    fun MaterialToolbar.changeRefreshing(isChangeIcon: Boolean) {
        if (isChangeIcon) {
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_back_24)
        }
    }

}