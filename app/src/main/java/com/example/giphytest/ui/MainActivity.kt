package com.example.giphytest.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.giphytest.R
import com.example.giphytest.constant.Constants
import com.example.giphytest.databinding.ActivityMainBinding
import com.jio.media.jioguard.base.BaseActivity

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUi()
        setUpListener()
    }


    override fun setUpUi() {
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.materialToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.materialToolbar.setNavigationOnClickListener {
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
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