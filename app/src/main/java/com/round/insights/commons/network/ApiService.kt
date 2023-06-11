package com.round.insights.commons.network

import com.round.insights.app.matches.data.repository.response.RoundMatchesResponse
import com.round.insights.app.matches.data.repository.response.RoundResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    fun getRoundMatches(@Url url: String?): Call<RoundMatchesResponse>

    @GET
    fun getRoundsInformation(@Url url: String?): Call<List<RoundResponse>>
}
