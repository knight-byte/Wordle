package com.knightbyte.wordle.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knightbyte.wordle.presentation.components.UserKeyboard
import com.knightbyte.wordle.presentation.components.WordleGrid
import com.knightbyte.wordle.presentation.ui.theme.MyBlack
import com.knightbyte.wordle.presentation.ui.theme.MyLightGray
import com.knightbyte.wordle.presentation.ui.theme.MyWhite
import com.knightbyte.wordle.presentation.viewmodel.WordleViewModel
import com.knightbyte.wordle.utils.Resource

@Composable
fun HomeScreen(
    wordleViewModel: WordleViewModel
) {
    when (wordleViewModel.word.value) {

        is Resource.Error -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "${wordleViewModel.word.value.message.toString()} ",
                        fontSize = 20.sp,
                        color = Color.Black

                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        modifier = Modifier
                            .background(MyLightGray)
                            .padding(10.dp)
                            .clickable {
                                wordleViewModel.getWord()
                            },
                        text = "Try Again",
                        fontSize = 20.sp,
                        color = Color.Black

                    )
                }

            }
        }
        is Resource.Empty, is Resource.Loading -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {

            val grid = wordleViewModel.userGrid
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MyWhite),
            ) {
                Column(Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Wordle",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = MyBlack,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Bold

                        )
                    )
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        if (wordleViewModel.won.value) {
                            Text(
                                text = "You Won!",
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = MyBlack,
                                    fontSize = 20.sp

                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        WordleGrid(grid)
                        Spacer(modifier = Modifier.height(50.dp))
                        UserKeyboard(
                            keys = wordleViewModel.userKeys.value,
                            wordleViewModel = wordleViewModel
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                }
            }
        }
    }
}