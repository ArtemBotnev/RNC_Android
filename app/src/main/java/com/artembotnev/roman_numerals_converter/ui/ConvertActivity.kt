package com.artembotnev.roman_numerals_converter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.artembotnev.roman_numerals_converter.theme.AppTheme
import com.artembotnev.roman_numerals_converter.theme.textError
import kotlinx.coroutines.FlowPreview

class ConvertActivity : AppCompatActivity() {

    private val viewModel = ConvertViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme(darkTheme = true) {
                BuildContent()
            }
//            AppTheme {
//                BuildContent()
//            }
        }
    }

    @Composable
    private fun BuildContent() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "RomanNumeralsConverter") }
                )
            },
            content = { InputTextFields() }
        )
    }

    @OptIn(FlowPreview::class)
    @Composable
    private fun InputTextFields() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state = viewModel.viewState.collectAsState().value
            OutlinedTextField(
                value = state.romanText,
                onValueChange = viewModel::updateRomanInput,
                label = {
                    Text(
                        text = "Roman",
                        fontFamily = FontFamily.SansSerif,
                        fontStyle = FontStyle.Italic,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                isError = state.romanInputError
            )
            if (state.romanInputError) {
                Text(
                    text = "Use only M, C, D, X, L, V, I letter",
                    style = textError
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(
                value = state.arabicText,
                onValueChange = viewModel::updateArabicInput,
                label = {
                    Text(
                        text ="Arabic",
                        fontFamily = FontFamily.SansSerif,
                        fontStyle = FontStyle.Italic,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                isError = state.arabicInputError
            )
            if (state.arabicInputError) {
                Text(
                    text = "Use only numbers",
                    style = textError
                )
            }
        }
    }

//    @Preview(showBackground = true, name = "Main preview")
//    @Composable
//    fun CallCardPreview() {
//        AppTheme(darkTheme = true) {
//            BuildContent()
//        }
//    }

    //                error = "Use only M, C, D, X, L, V, I letter"
}
