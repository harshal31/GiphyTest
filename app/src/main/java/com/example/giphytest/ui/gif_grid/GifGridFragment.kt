package com.example.giphytest.ui.gif_grid

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.giphytest.R
import com.example.giphytest.base.EndlessScrollListener
import com.example.giphytest.constant.snack
import com.example.giphytest.databinding.FragmentGifGridBinding
import com.example.giphytest.ui.MainViewModel
import com.example.giphytest.ui.gif_detail.GifModel
import com.jio.media.jioguard.base.BaseFragment

class GifGridFragment : BaseFragment<FragmentGifGridBinding, GifModel>() {

    private lateinit var shareModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shareModel = getShareModel()
        viewModel.initAdapter(shareModel)
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setUpListener()
    }

    override fun setUpUi() {
        clearAdapterAndThenCallApi()
    }

    override fun setUpListener() {
        viewModel.trendingGiphyResponse.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.adapter.addData(it.data)
            }
        }

        shareModel.menuRatingClicked.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                if (viewModel.previousRating != it) {
                    clearAdapterAndThenCallApi(it)
                } else {
                    binding.root.snack("Please Select Different option its already selected")
                }
            }
        }

        binding.gifRecyclerView.addOnScrollListener(object : EndlessScrollListener(binding.gifRecyclerView.layoutManager as StaggeredGridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.requestTrendingGiphy(totalItemsCount)
            }
        })

        binding.giphySwipeToRefresh.setOnRefreshListener { clearAdapterAndThenCallApi() }
    }


    private fun clearAdapterAndThenCallApi(rating: String = "g") {
        viewModel.adapter.clearData()
        viewModel.requestTrendingGiphy(rating = rating)
    }

    override val getModel: Class<GifModel> = GifModel::class.java

    override val layoutId: Int = R.layout.fragment_gif_grid

}