package com.knightbyte.wordle.repository

import com.knightbyte.wordle.utils.Resource

interface WordlistInterface {
    suspend fun getWord():Resource<String>
}