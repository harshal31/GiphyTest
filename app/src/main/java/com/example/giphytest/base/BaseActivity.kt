package com.jio.media.jioguard.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.giphytest.BR
import com.example.giphytest.R
import com.example.giphytest.base.BaseErrorCallback
import com.example.giphytest.constant.snack

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    BaseErrorCallback, LifecycleOwner {

    lateinit var binding: T
    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this).get(getModel)
        viewModel.setCallBack(this)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
    }

    protected fun setToolbar(toolbar: androidx.appcompat.widget.Toolbar) {
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    protected fun setHomeEnabled(value: Boolean = false) {
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
        supportActionBar?.setHomeButtonEnabled(value)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else              -> super.onOptionsItemSelected(item)
    }

    override fun onNetworkChanged(message: String) {
        binding.root.snack(message)
    }


    protected abstract fun setUpUi()
    protected abstract val getModel: Class<V>
    protected abstract val layoutId: Int
}
