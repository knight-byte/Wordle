package com.knightbyte.wordle.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knightbyte.wordle.presentation.ui.theme.MyBlack
import com.knightbyte.wordle.presentation.ui.theme.MyLightGray
import com.knightbyte.wordle.presentation.ui.theme.MyWhite
import com.knightbyte.wordle.presentation.ui.theme.Shapes
import com.knightbyte.wordle.utils.WordleStatus

@Composable
fun WordleGrid(
    userGrid: SnapshotStateList<List<WordleStatus>>
) {
    val grid = listOf(
        listOf(
            WordleStatus.Correct("A"),
            WordleStatus.InCorrect("B"),
            WordleStatus.InCorrect("C"),
            WordleStatus.InCorrect("D"),
            WordleStatus.InCorrect("D"),

            ),
        listOf(
            WordleStatus.Partial("A"),
            WordleStatus.InCorrect("B"),
            WordleStatus.InCorrect("B"),
            WordleStatus.InCorrect("C"),
            WordleStatus.InCorrect("D"),
        ),
        listOf(
            WordleStatus.InCorrect("A"),
            WordleStatus.InCorrect("B"),
            WordleStatus.InCorrect("C"),
            WordleStatus.Partial("D"),
            WordleStatus.Partial("D")
        ),
        listOf(
            WordleStatus.Correct("A"),
            WordleStatus.InCorrect("B"),
            WordleStatus.Partial("C"),
            WordleStatus.Partial("D"),
            WordleStatus.Partial("D")
        ),
        listOf(
            WordleStatus.Correct("A"),
            WordleStatus.Correct("A"),
            WordleStatus.InCorrect("B"),
            WordleStatus.InCorrect("C"),
            WordleStatus.InCorrect("D"),
        ),
        listOf(
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
            WordleStatus.Empty(),
        )
    )
    Column(modifier = Modifier.background(MyWhite)) {
        userGrid.forEach { row ->
            Row {
                row.forEach { single ->
                    SingleGridBox(
                        character = single.character,
                        background = single.background
                    )

                }
            }

        }
    }
}

@Composable
fun SingleGridBox(
    character: String = "",
    background: Color = MyWhite,
    border: Color = MyLightGray
) {
    val dim = 56.dp
    Card(
        modifier = Modifier
            .padding(3.dp)
            .width(dim)
            .height(dim)
            .border(width = 1.dp, color = border)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(background), contentAlignment = Alignment.Center
        ) {
            Text(
                text = character,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = MyBlack,
                    fontSize = 16.sp

                )
            )
        }

    }
}
