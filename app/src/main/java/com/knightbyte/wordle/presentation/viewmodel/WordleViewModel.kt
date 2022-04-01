package com.knightbyte.wordle.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knightbyte.wordle.repository.WordListRepo
import com.knightbyte.wordle.utils.CUSTOM_CHECK_DEBUG_LOG
import com.knightbyte.wordle.utils.Resource
import com.knightbyte.wordle.utils.WordleStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WordleViewModel @Inject constructor(
    private  val wordListRepo: WordListRepo
) : ViewModel() {

    val won = mutableStateOf(false)
    var level = 1
    val word :MutableState<Resource<String>>  = mutableStateOf(Resource.Empty())
    val gameOver:MutableState<Boolean> = mutableStateOf(false)

    init {
        getWord()
    }


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

    private fun resetGrid(){
        for (i in 0..5) {
            val tempList = userGrid[i].toMutableList()
            userGrid[i] = tempList.map{
                WordleStatus.Empty()
            }
        }
    }

    private  fun resetKeyboard(){
        for (i in 0..3) {
            val tempKeys = userKeys.value
            userKeys.value = tempKeys.mapIndexed { _, keys ->
                val data = keys.map { key ->
                    WordleStatus.Input(key.character)
                }
                data

            }
        }
    }

    var userGrid = _userGrid
    var userKeys = _userKeyboard
    private var userPositionCol = mutableStateOf(0)
    private var userRowPosition = mutableStateOf(0)
    fun getWord(){
        word.value = Resource.Loading()
        viewModelScope.launch {
            word.value = wordListRepo.getWord()
            userKeys.value = _userKeyboard.value
            resetGrid()
            resetKeyboard()
            won.value = false
            gameOver.value = false
            userPositionCol.value = 0
            userRowPosition.value =0
        }
    }

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
        if(won.value || gameOver.value) return
        if (userPositionCol.value >= 4 && userRowPosition.value < 6) {
            var count = 0
            val tempList = userGrid[userRowPosition.value].toMutableList()
            userGrid[userRowPosition.value] = tempList.mapIndexed { index, wordleStatus ->
                val character = wordleStatus.character.lowercase()

                val returnData = if (word.value.data?.get(index).toString() != character) {
                    if (character in word.value.data!!) {
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
            if(userRowPosition.value>5){
                gameOver.value = true
            }
            userPositionCol.value = 0
        }


    }

    private fun updateKeyboard() {
        if(won.value || gameOver.value) return
        val tempKeys = userKeys.value
        val userEntered = userGrid[userRowPosition.value]
        userKeys.value = tempKeys.mapIndexed { _, keys ->
            val data = keys.map { key ->
                var returnData = key
                userEntered.forEach {
                    if (it.character == key.character) {
                        Log.d(CUSTOM_CHECK_DEBUG_LOG,"ENTERED HERE ${key.character}")
                        returnData = it
                    }
                }
                returnData
            }

            data

        }

    }

    fun backspace(){
        if(won.value || gameOver.value || userRowPosition.value>5) return
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