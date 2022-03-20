package com.knightbyte.wordle.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.knightbyte.wordle.utils.CUSTOM_CHECK_DEBUG_LOG
import com.knightbyte.wordle.utils.WordleStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WordleViewModel @Inject constructor() : ViewModel() {

    private val _userGrid = mutableStateListOf<List<WordleStatus>>(

        listOf(
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
        ),
        listOf(
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
        ),
        listOf(
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
        ),
        listOf(
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
        ),
        listOf(
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
        ),
        listOf(
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
        )
    )


    private val _userKeyboard: MutableState<List<List<WordleStatus>>> = mutableStateOf(
        listOf(
            listOf(
                WordleStatus.Input("Q"),
                WordleStatus.Input("W"),
                WordleStatus.Input("E"),
                WordleStatus.Input("R"),
                WordleStatus.Input("T"),
                WordleStatus.Input("Y"),
                WordleStatus.Input("U"),
                WordleStatus.Input("I"),
                WordleStatus.Input("O"),
                WordleStatus.Input("P")
            ),
            listOf(
                WordleStatus.Input("A"),
                WordleStatus.Input("S"),
                WordleStatus.Input("D"),
                WordleStatus.Input("F"),
                WordleStatus.Input("G"),
                WordleStatus.Input("H"),
                WordleStatus.Input("J"),
                WordleStatus.Input("K"),
                WordleStatus.Input("L"),
            ),
            listOf(
                WordleStatus.Input("-1"),
                WordleStatus.Input("Z"),
                WordleStatus.Input("X"),
                WordleStatus.Input("C"),
                WordleStatus.Input("V"),
                WordleStatus.Input("B"),
                WordleStatus.Input("N"),
                WordleStatus.Input("M"),
                WordleStatus.Input("-2")
            )

            )
    )
    val userGrid = _userGrid
    val userKeys = _userKeyboard
    private val userPositionCol = mutableStateOf(0)
    private val userRowPosition = mutableStateOf(0)

    val won = mutableStateOf(false)
    var level = 1
    private val word = "apple"

    fun updateBoxValue(character: String) {
        if(won.value) return
        if(userPositionCol.value<5 && (userGrid[userRowPosition.value][4] !is WordleStatus.Input)) {
            Log.d(CUSTOM_CHECK_DEBUG_LOG, "INP - $character")
            val tempData = userGrid[userRowPosition.value].toMutableList()
            tempData[userPositionCol.value] = WordleStatus.Input(character = character)
            userGrid[userRowPosition.value] = tempData
            if (userPositionCol.value <4) {
                userPositionCol.value = userPositionCol.value + 1
            }
        }
    }

    fun updateGrid() {
        if(won.value) return
        if (userPositionCol.value >= 4 && userRowPosition.value < 6) {
            var count = 0
            val tempList = userGrid[userRowPosition.value].toMutableList()
            userGrid[userRowPosition.value] = tempList.mapIndexed { index, wordleStatus ->
                val character = wordleStatus.character.lowercase()

                val returnData = if (word[index].toString() != character) {
                    if (character in word) {
                        WordleStatus.Partial(wordleStatus.character)
                    } else {
                        WordleStatus.InCorrect(wordleStatus.character)
                    }
                } else {
                    count+=1
                    WordleStatus.Correct(wordleStatus.character)
                }

                returnData
            }
            updateKeyboard()
            if(count==5){
                won.value = true
                level = userRowPosition.value
            }
            userRowPosition.value = userRowPosition.value + 1
            userPositionCol.value = 0
        }


    }

    fun updateKeyboard() {
        if(won.value) return
        val tempKeys = userKeys.value
        val userEntered = userGrid[userRowPosition.value]
        userKeys.value = tempKeys.mapIndexed { _, keys ->
            val data = keys.map { key ->
                var returnData = key
                userEntered.forEach {
                    if (it.character == key.character) {
                        Log.d(CUSTOM_CHECK_DEBUG_LOG,"ENTERED JERE ${key.character}")
                        returnData = it
                    }
                }
                returnData
            }

            data

        }

    }

    fun backspace(){
        if (userPositionCol.value>=0) {
            val tempData = userGrid[userRowPosition.value].toMutableList()
            tempData[userPositionCol.value] = WordleStatus.Empty()
            userGrid[userRowPosition.value] = tempData
            if(userPositionCol.value>0) {
                userPositionCol.value = userPositionCol.value - 1
            }
        }
    }

}