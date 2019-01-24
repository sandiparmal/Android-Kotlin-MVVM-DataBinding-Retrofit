package infosys.com.kotlinmvvmsample.service.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import android.util.Log
import infosys.com.kotlinmvvmsample.service.model.Fact
import infosys.com.kotlinmvvmsample.service.model.FactResponse
import infosys.com.kotlinmvvmsample.view.ui.FactListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class FactRepository {
    private val HTTPS_API_FACTS_URL = "https://dl.dropboxusercontent.com"
    private var factServices: FactServices? = null

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(HTTPS_API_FACTS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        factServices = retrofit.create(FactServices::class.java)
    }

    companion object {
        private var factRepository: FactRepository? = null
        @Synchronized
        @JvmStatic
        fun getInstance(): FactRepository {
            if (factRepository == null) {
                factRepository = FactRepository()
            }
            return factRepository!!
        }
    }


    fun getFactList(): LiveData<FactResponse> {
        val data = MutableLiveData<FactResponse>()
        Log.d(FactListFragment.TAG, "data response : "+data)
        factServices?.getFactList()?.enqueue(object : Callback<FactResponse> {
            override fun onResponse(call: Call<FactResponse>, response: Response<FactResponse>) {
                data.value=response.body()
            }

            override fun onFailure(call: Call<FactResponse>, t: Throwable) {
                Log.d(FactListFragment.TAG, "Error While response : "+t.printStackTrace())
                data.value=null
            }
        })

        return data
    }
}