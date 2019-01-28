package infosys.com.kotlinmvvmsample.service.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import infosys.com.kotlinmvvmsample.database.DatabaseCache
import infosys.com.kotlinmvvmsample.extensions.customPrefs
import infosys.com.kotlinmvvmsample.extensions.get
import infosys.com.kotlinmvvmsample.extensions.set
import infosys.com.kotlinmvvmsample.service.model.FactResponse
import infosys.com.kotlinmvvmsample.utils.ConnectivityUtils
import infosys.com.kotlinmvvmsample.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class FactRepository {
    private var factServices: FactServices? = null

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
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


    /**
     * If network is available then load updated data from server and saved it in SQLite database.
     * If network is not available then will load data from SQLite database
     *
     * @param context Context
     * @param databaseCache DatabaseCache
     *
     * @return FactResponse
     */
    fun getFactList(context: Context, databaseCache: DatabaseCache): LiveData<FactResponse> {
        val data = MutableLiveData<FactResponse>()
        val prefs = customPrefs(context, Constants.APP_SHARED_PREFS)

        if (databaseCache.getAllFacts().isEmpty()) {
            // no data in database, load from server
            data.value = fetchFactsFromServer(data, databaseCache, context)

        } else {
            if (ConnectivityUtils.isNetworkAvailable(context)) {
                // data is present in db but network is present so will load updated data
                data.value = fetchFactsFromServer(data, databaseCache, context)
            } else {
                // data is present in db but nrtwork is not present so will data from db
                Log.d(Constants.APP_TAG, "data response from database: " + data)
                data.value = FactResponse(prefs[Constants.PREFS_TOOLBAR]!!, databaseCache.getAllFacts())
            }

        }
        return data
    }

    /**
     * Load data from server and saved it in database, delete if any available in db
     *
     * @param data MutableLiveData<FactResponse>
     * @param databaseCache DatabaseCache
     * @param context Context
     *
     * @return FactResponse
     */
    private fun fetchFactsFromServer(data: MutableLiveData<FactResponse>, databaseCache: DatabaseCache, context: Context): FactResponse? {
        val prefs = customPrefs(context, Constants.APP_SHARED_PREFS)
        factServices?.getFactList()?.enqueue(object : Callback<FactResponse> {
            override fun onResponse(call: Call<FactResponse>, response: Response<FactResponse>) {
                Log.d(Constants.APP_TAG, "data response from server: " + response.body())
                data.value = response.body()

                // delete all facts from database and insert updated one
                databaseCache.deleteAllFacts()
                databaseCache.insert(response.body()?.rows?.toMutableList()!!)

                // add Toolbar in Preference
                prefs[Constants.PREFS_TOOLBAR] = response.body()?.title

            }

            override fun onFailure(call: Call<FactResponse>, t: Throwable) {
                Log.d(Constants.APP_TAG, "Error While response : " + t.printStackTrace())
                data.value = null
            }
        })

        return data.value
    }
}