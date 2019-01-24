package infosys.com.kotlinmvvmsample.service.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import infosys.com.kotlinmvvmsample.service.model.Fact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class FactRepository {
    private val HTTPS_API_FACTS_URL = "https://dl.dropboxusercontent.com"
    private var factServices: FactServices?=null

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


    fun getFactList(): LiveData<List<Fact>> {
        val data = MutableLiveData<List<Fact>>()

        factServices?.getFactList()?.enqueue(object : Callback<List<Fact>> {
            override fun onResponse(call: Call<List<Fact>>, response: Response<List<Fact>>) {
                data.value=response.body()
            }

            override fun onFailure(call: Call<List<Fact>>, t: Throwable) {
                // TODO better error handling in part #2 ...
                data.value=null
            }
        })

        return data
    }
}