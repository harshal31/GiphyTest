package com.jio.media.jioguard.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.giphytest.base.BaseErrorCallback
import com.example.giphytest.sealedAndenums.ApiStatus
import com.example.giphytest.sealedAndenums.ServerStatus
import com.jio.media.jioguard.data.network.ApiHelper
import com.jio.media.jioguard.data.network.ApiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val dataManager: ApiHelper = ApiManager
    var commonErrorCallback: BaseErrorCallback? = null
    val serverStatus = MutableLiveData<ServerStatus>()
    val isDisplayBackButton = MutableLiveData(false)



    suspend fun <T> safeCoroutineCall(retroFun: suspend () -> Response<T>, isShowProgress: Boolean = false, block: (apiData: T?, apiStatus: ApiStatus) -> Unit) {
        startLoader(isShowProgress)
        withContext(Dispatchers.IO) {
            try {
                val response = retroFun.invoke()
                if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                    setServerErrorMsg(ServerStatus.ON_SUCCESS)
                    dispatchOnMain {
                        block(response.body(), ApiStatus.ON_API_SUCCESS)
                    }

                } else {
                    setServerErrorMsg(ServerStatus.ON_SERVER_ERROR)
                    dispatchOnMain {
                        block(null, ApiStatus.ON_API_FAILURE)
                    }

                }
            } catch (e: Throwable) {
                if (e is IOException) {
                    setServerErrorMsg(ServerStatus.NO_INTERNET)
                    commonErrorCallback?.onNetworkChanged()
                } else {
                    setServerErrorMsg(ServerStatus.ON_SERVER_ERROR)
                }
                dispatchOnMain {
                    block(null, ApiStatus.ON_API_FAILURE)
                }

            }
        }
    }

    private fun setServerErrorMsg(status: ServerStatus) {
        serverStatus.postValue(status)
    }


    //set baseErrorCallback from app level
    fun setCallBack(errorCallback: BaseErrorCallback) {
        this.commonErrorCallback = errorCallback
    }

    private suspend fun dispatchOnMain(block: () -> Unit) {
        withContext(Dispatchers.Main) {
            block()
        }
    }


    private fun startLoader(isShowProgress: Boolean) {
        if (isShowProgress.not()) {
            serverStatus.value = ServerStatus.ON_PROGRESS
        }
    }

    override fun onCleared() {
        super.onCleared()
        commonErrorCallback = null
    }

}
