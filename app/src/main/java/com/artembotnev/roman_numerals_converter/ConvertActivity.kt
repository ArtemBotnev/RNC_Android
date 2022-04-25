package com.artembotnev.roman_numerals_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class ConvertActivity : AppCompatActivity() {

    private val viewModel = ConvertViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContent {
//            AppTheme {
//                BuildContent()
//            }
            InputTextField()
        }
    }

    @Composable
    fun InputTextField() {

        Column(
            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.viewState.collectAsState().value.romanText,
                onValueChange = viewModel::updateRomanInput,
                label = { Text("Roman") }
            )
            OutlinedTextField(
                value = viewModel.viewState.collectAsState().value.arabicText,
                onValueChange = viewModel::updateArabicInput,
                label = { Text("Arabic") }
            )
        }
    }

    @Preview(showBackground = true, name = "Main preview")
    @Composable
    fun CallCardPreview() {
//        AppTheme(darkTheme = true) {
//        }

        InputTextField()
    }
}
