package com.example.insights.data.network

import com.example.insights.data.RoundMatchesResponse
import com.example.insights.data.RoundResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    fun getRoundMatches(@Url url: String?): Call<RoundMatchesResponse>

    @GET
    fun getRoundsInformation(@Url url: String?): Call<List<RoundResponse>>
}
