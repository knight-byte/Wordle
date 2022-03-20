package com.knightbyte.wordle.utils

import androidx.compose.ui.graphics.Color
import com.knightbyte.wordle.presentation.ui.theme.*

sealed class WordleStatus(
    val character: String = "",
    val background: Color = MyWhite
) {
    class Correct(
        character: String,
    ) : WordleStatus(character, MyGreen)

    class Partial(
        character: String,
    ) : WordleStatus(character, MyYellow)

    class InCorrect(
        character: String,
    ) : WordleStatus(character, MyLightGray)

    class Input(
        character: String
    ): WordleStatus(character, MyWhite)
    class Empty : WordleStatus("",background = MyWhite)

}


