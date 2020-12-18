package com.example.giphytest.ui.gif_detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import com.example.giphytest.R
import com.example.giphytest.constant.Constants
import com.example.giphytest.databinding.FragmentDetailBinding
import com.example.giphytest.ui.MainViewModel
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jio.media.jioguard.base.BaseFragment

class GifDetailFragment : BaseFragment<FragmentDetailBinding, GifDetailViewModel>() {

    private lateinit var shareModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = Constants.DURATION
            isElevationShadowEnabled = true
            setAllContainerColors(MaterialColors.getColor(requireContext(), R.attr.colorSurface, Color.BLACK))
        }
        shareModel = getShareModel()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.parentConstraintLayout, shareModel.gifDetailData.value?.id)
        binding.model = shareModel
        setUpUi()
        setUpListener()
    }

    override fun setUpUi() {

    }

    override fun setUpListener() {

    }

    override val getModel: Class<GifDetailViewModel> = GifDetailViewModel::class.java
    override val layoutId: Int = R.layout.fragment_detail
    override val isSaveFragmentState: Boolean = false
}