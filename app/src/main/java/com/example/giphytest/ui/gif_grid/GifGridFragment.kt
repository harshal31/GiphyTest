package com.example.giphytest.ui.gif_grid

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.giphytest.R
import com.example.giphytest.base.EndlessScrollListener
import com.example.giphytest.constant.Constants
import com.example.giphytest.constant.snack
import com.example.giphytest.databinding.FragmentGifGridBinding
import com.example.giphytest.ui.MainViewModel
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialElevationScale
import com.jio.media.jioguard.base.BaseFragment

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class GifGridFragment : BaseFragment<FragmentGifGridBinding, GifModel>() {

    private lateinit var shareModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shareModel = getShareModel()
        viewModel?.initAdapter(shareModel)
        super.onViewCreated(view, savedInstanceState)
        ViewGroupCompat.setTransitionGroup(binding.gifRecyclerView, true)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        setUpUi()
        setUpListener()
    }

    override fun setUpUi() {
        if (viewModel?.getSaveStateAdapter?.value == null) {
            clearAdapterAndThenCallApi()
        }
    }

    override fun setUpListener() {
        viewModel?.trendingGiphyResponse?.observe(viewLifecycleOwner) {
            it?.let {
                viewModel?.adapter?.addData(it.data)
            }
        }

        shareModel.menuRatingClicked.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                if (viewModel?.previousRating != it) {
                    clearAdapterAndThenCallApi(it)
                } else {
                    binding.root.snack("Please Select Different option its already selected")
                }
            }
        }

        binding.gifRecyclerView.addOnScrollListener(object : EndlessScrollListener(binding.gifRecyclerView.layoutManager as StaggeredGridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel?.requestTrendingGiphy(totalItemsCount)
            }
        })

        shareModel.gifItemClick.observe(viewLifecycleOwner) {
            it?.let {
                viewModel?.saveAdapterState()
                exitTransition = Hold().apply {
                    duration = Constants.DURATION
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = Constants.DURATION
                }

                shareModel.gifDetailData.value = it.second
                val action = GifGridFragmentDirections.actionGifGridFragmentToGifDetailFragment()
                val extras = FragmentNavigatorExtras(it.first to (it.second.id ?: ""))
                it.first.findNavController().navigate(action, extras)
                shareModel.gifItemClick.value = null
            }
        }

        viewModel?.getSaveStateAdapter?.observe(viewLifecycleOwner) {
            it?.let {
                viewModel?.adapter?.setListData(it.toSet().toMutableList())
            }
        }

        binding.giphySwipeToRefresh.setOnRefreshListener { clearAdapterAndThenCallApi() }
    }


    private fun clearAdapterAndThenCallApi(rating: String = "g") {
        viewModel?.adapter?.clearData()
        viewModel?.requestTrendingGiphy(rating = rating)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel?.saveAdapterState()

    }

    override val getModel: Class<GifModel> = GifModel::class.java

    override val layoutId: Int = R.layout.fragment_gif_grid

    override val isSaveFragmentState: Boolean = true

}