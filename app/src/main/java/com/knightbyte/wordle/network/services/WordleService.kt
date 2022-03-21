package com.knightbyte.wordle.network.services
import com.knightbyte.wordle.network.responses.wordResponse
import  retrofit2.http.GET
interface WordleService {

    @GET("getword")
    suspend fun getWord():wordResponse
}