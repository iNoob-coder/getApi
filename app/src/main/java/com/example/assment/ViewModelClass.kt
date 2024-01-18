package com.example.assment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assment.model.PostData
import com.example.assment.repository.Repository

class ViewModelClass(application: Application): AndroidViewModel(application)  {
    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> get() = _isInternetAvailable

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isInternetAvailable.postValue(true)
        }

        override fun onLost(network: Network) {
            _isInternetAvailable.postValue(false)
        }
    }

    var repository: Repository? = null

    init {
        repository = Repository()
        registerConnectivityCallback()
    }

    private fun registerConnectivityCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onCleared() {
        super.onCleared()
        unregisterConnectivityCallback()
    }

    private fun unregisterConnectivityCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    fun showToastIfNoInternet() {
        if (isInternetAvailable.value == false) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkInternetConnectivity(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isInternetAvailable.postValue(true)
            }

            override fun onLost(network: Network) {
                _isInternetAvailable.postValue(false)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        }
    }

    fun getPostData(): MutableLiveData<PostData?> {
        return repository!!.fetchPostData()
    }
}