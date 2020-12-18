package com.example.giphytest.ui.gif_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.giphytest.R
import com.example.giphytest.data.model.DataItem
import com.example.giphytest.databinding.ViewholderGifGridBinding
import com.example.giphytest.ui.MainViewModel


class GifGridAdapter : RecyclerView.Adapter<GifGridAdapter.GifGridViewHolder>() {

    var list = mutableListOf<DataItem>()

    var viewModel: MainViewModel? = null

    fun addData(data: List<DataItem>) {
        list.addAll(data)
        if (list.size <= 25) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(list.size - 1, data.size)
        }
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    fun setListData(data: MutableList<DataItem>) {
        list = data
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifGridViewHolder {
        val binding = DataBindingUtil.inflate<ViewholderGifGridBinding>(LayoutInflater.from(parent.context), R.layout.viewholder_gif_grid, parent, false)
        return GifGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifGridViewHolder, position: Int) = holder.bindData(list[position])

    override fun getItemCount(): Int = list.size


    inner class GifGridViewHolder(val bind: ViewholderGifGridBinding) : RecyclerView.ViewHolder(bind.root) {
        private val constraintSet = ConstraintSet()

        fun bindData(data: DataItem) {
            ViewCompat.setTransitionName(bind.ivGiphy, data.id)
            bind.ivGiphy.setOnClickListener {
                viewModel?.gifItemClick?.value = Pair(it, list[adapterPosition])
            }
            with(constraintSet) {
                val ratio = "${data.images?.previewGif?.width}:${data.images?.previewGif?.height}"
                clone(bind.rootConstraint)
                setDimensionRatio(bind.ivGiphy.id, ratio)
                applyTo(bind.rootConstraint)
            }
            bind.viewModel = viewModel
            bind.dataItem = data
        }
    }

}
