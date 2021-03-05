package com.priyank.mvvmdemo.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.JsonSyntaxException
import com.priyank.mvvmdemo.BuildConfig
import com.priyank.mvvmdemo.R
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

abstract class BaseFragment : Fragment() {

    abstract fun getBaseViewModel(): BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachBaseObserver()
    }

    private fun attachBaseObserver() {
        getBaseViewModel()?.exceptionLiveData?.observe(this, {
            (activity as BaseActivity).hideProgress()
            it?.apply {
                when (this) {
                    is HttpException -> {
                        when (this.code()) {
                            HttpsURLConnection.HTTP_UNAUTHORIZED -> (activity as BaseActivity).showMessage(
                                getString(R.string.exception_error_unauthorized)
                            )
                            HttpsURLConnection.HTTP_FORBIDDEN -> (activity as BaseActivity).showMessage(
                                getString(R.string.exception_error_forbidden)
                            )
                            HttpsURLConnection.HTTP_INTERNAL_ERROR -> (activity as BaseActivity).showMessage(
                                getString(R.string.exception_error_server)
                            )
                            HttpsURLConnection.HTTP_BAD_REQUEST -> (activity as BaseActivity).showMessage(
                                getString(R.string.exception_error_bad_request)
                            )
                            else -> this.localizedMessage
                        }
                    }
                    is JsonSyntaxException -> {
                        (activity as BaseActivity).showMessage(getString(R.string.exception_error_unparceble))
                    }
                    else -> {
                        if (BuildConfig.DEBUG) {
                            (activity as BaseActivity).showMessage(this.message!!)
                        } else {
                            (activity as BaseActivity).showMessage(getString(R.string.something_went_wrong))
                        }
                    }
                }
            }
        })
    }
}