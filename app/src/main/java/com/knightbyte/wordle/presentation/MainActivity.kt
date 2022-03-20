package com.knightbyte.wordle.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.knightbyte.wordle.presentation.ui.screens.HomeScreen
import com.knightbyte.wordle.presentation.ui.theme.WordleTheme
import com.knightbyte.wordle.presentation.viewmodel.WordleViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val wordleViewModel: WordleViewModel = hiltViewModel()
            WordleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen(wordleViewModel = wordleViewModel)
                }
            }
        }
    }
}

