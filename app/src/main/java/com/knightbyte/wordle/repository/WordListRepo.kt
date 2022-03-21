package com.knightbyte.wordle.repository

import com.knightbyte.wordle.network.services.WordleService
import com.knightbyte.wordle.utils.Resource
import java.io.IOException
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class WordListRepo @Inject constructor(
    private val wordleService: WordleService
) :WordlistInterface {
    override suspend fun getWord(): Resource<String> {
        return try {
            val wordResponse = wordleService.getWord()


            return Resource.Success(wordResponse.word.lowercase())
        } catch (t: Throwable) {
            val error = when (t) {
                is IOException -> "Network Failure"
                else -> "Conversion Error"
            }
            Resource.Error(error)
        }
    }
}