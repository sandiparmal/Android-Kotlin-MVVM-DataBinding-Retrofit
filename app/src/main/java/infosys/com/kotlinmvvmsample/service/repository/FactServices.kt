package infosys.com.kotlinmvvmsample.service.repository

import infosys.com.kotlinmvvmsample.service.model.FactResponse
import infosys.com.kotlinmvvmsample.utils.Constants
import retrofit2.Call
import retrofit2.http.GET

interface FactServices {
    @GET(Constants.FACT_URL)
    fun getFactList(): Call<FactResponse>


}