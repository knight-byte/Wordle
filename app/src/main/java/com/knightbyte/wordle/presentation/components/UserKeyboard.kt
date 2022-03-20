package com.knightbyte.wordle.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knightbyte.wordle.R
import com.knightbyte.wordle.presentation.ui.theme.MyBlack
import com.knightbyte.wordle.presentation.ui.theme.MyLightGray
import com.knightbyte.wordle.presentation.ui.theme.MyWhite
import com.knightbyte.wordle.presentation.viewmodel.WordleViewModel
import com.knightbyte.wordle.utils.WordleStatus


@Composable
fun UserKeyboard(
    keys: List<List<WordleStatus>>,
    wordleViewModel: WordleViewModel
) {
    Column(
        modifier = Modifier.background(MyWhite),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        keys.forEach { row ->
            Row(horizontalArrangement = Arrangement.Center) {
                row.forEach { single ->
                    var actionKey: Int? = null
                    var onClick: () -> Unit = {}
                    if (single.character == "-1") {
                        actionKey = R.drawable.ic_done_icon
                        onClick = {
                            wordleViewModel.updateGrid()
                        }
                    } else if (single.character == "-2") {
                        actionKey = R.drawable.ic_backspace_icon
                        onClick = {
                            wordleViewModel.backspace()
                        }
                    } else {
                        onClick = {
                            wordleViewModel.updateBoxValue(single.character)
                        }
                    }
                    SingleKeyBoardKey(
                        character = single.character,
                        background = single.background,
                        actionKey = actionKey,
                        onClick = onClick
                    )

                }
            }
            Spacer(modifier = Modifier.height(5.dp))

        }
    }
}

@Composable
fun SingleKeyBoardKey(
    character: String,
    background: Color = MyWhite,
    onClick: () -> Unit = {},
    @DrawableRes actionKey: Int? = null
) {
    val width = if (actionKey == null) 32.dp else 46.dp
    Card(
        modifier = Modifier
            .padding(2.dp)
            .width(width)
            .height(46.dp)
            .border(width = 0.5.dp, color = MyLightGray)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(background)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            if (actionKey == null) {
                Text(
                    text = character,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = MyBlack,
                        fontSize = 12.sp
                    )
                )
            } else {
                Icon(
                    painter = painterResource(actionKey),
                    tint = MyBlack,
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp),
                    contentDescription = ""
                )
            }
        }

    }
}