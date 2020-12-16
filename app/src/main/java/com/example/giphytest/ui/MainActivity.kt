package com.example.giphytest.ui

import android.os.Bundle
import com.example.giphytest.R
import com.example.giphytest.constant.Constants
import com.example.giphytest.constant.setFragment
import com.example.giphytest.databinding.ActivityMainBinding
import com.example.giphytest.ui.gif_grid.GifGridFragment
import com.jio.media.jioguard.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUi()
        setUpListener()
    }

    override fun setUpUi() {
        setFragment(GifGridFragment(), supportFragmentManager, binding.fragmentLayoutContainer.id)
    }

    private fun setUpListener() {
        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.gRating -> {
                    viewModel.menuRatingClicked.value = Constants.G_RATING
                    true
                }
                R.id.pgRating -> {
                    viewModel.menuRatingClicked.value = Constants.PG_RATING
                    true
                }
                R.id.rRating -> {
                    viewModel.menuRatingClicked.value = Constants.R_RATING
                    true
                }
                R.id.rgRating -> {
                    viewModel.menuRatingClicked.value = Constants.RG_RATING
                    true
                }
                else -> true
            }
        }
    }

    override val getModel: Class<MainViewModel> = MainViewModel::class.java
    override val layoutId: Int = R.layout.activity_main
}