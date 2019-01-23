package infosys.com.kotlinmvvmsample.service.repository

import infosys.com.kotlinmvvmsample.service.model.Fact
import retrofit2.Call
import retrofit2.http.GET

interface FactServices {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getFactList(): Call<List<Fact>>


}