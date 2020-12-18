package com.jio.media.jioguard.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.example.giphytest.BR
import com.example.giphytest.base.BaseErrorCallback
import com.example.giphytest.constant.snack

/**
 * Created by Harshal Chaudhari on 16/3/19.
 */

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment(), LifecycleObserver,
    BaseErrorCallback {

    lateinit var baseActivity: BaseActivity<*, *>
    open lateinit var binding: T
    open var viewModel: V? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as BaseActivity<*, *>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewModel = if (isSaveFragmentState) ViewModelProvider(this, SavedStateViewModelFactory(baseActivity.application, this)).get(getModel) else ViewModelProvider(this).get(getModel)
        viewModel?.setCallBack(this)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    protected fun setHomeEnabled(value: Boolean) {
        baseActivity.supportActionBar?.setDisplayHomeAsUpEnabled(value)
        baseActivity.supportActionBar?.setHomeButtonEnabled(value)
    }

    override fun onNetworkChanged(message: String) {
        binding.root.snack("Please check your internet connection")
    }


    //this function is basically used if some fragment want activity level viewModelScope just call this function
    inline fun <reified G : BaseViewModel> getShareModel(): G =
        ViewModelProvider(baseActivity).get(G::class.java)

    protected abstract fun setUpUi()
    protected abstract fun setUpListener()
    protected abstract val getModel: Class<V>
    protected abstract val layoutId: Int
    protected abstract val isSaveFragmentState: Boolean
}
