package infosys.com.kotlinmvvmsample.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityUtils {
    companion object {
        fun isNetworkAvailable(mActivity: Activity): Boolean {
            val connectivityManager = mActivity.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }
    }
}