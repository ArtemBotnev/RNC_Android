package com.artembotnev.roman_numerals_converter

import RomanNumeralConverter
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

private const val INPUT_DELAY = 350L

class ConvertViewModel : ViewModel() {

    private val converter = RomanNumeralConverter()

    private val _viewState: MutableStateFlow<ConverterViewState> by lazy {
        MutableStateFlow(
            ConverterViewState(romanText = "", arabicText = "",)
        )
    }

    val viewState: StateFlow<ConverterViewState> = _viewState

    fun updateRomanInput(text: String, isInputUpdate: Boolean = true) {
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(romanText = text)
            )
            if (isInputUpdate) text.romanToArabic()
        }
    }

    fun updateArabicInput(text: String, isInputUpdate: Boolean = true) {
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(arabicText = text)
            )
            if (isInputUpdate) text.arabicToRoman()
        }
    }

    private suspend inline fun String.romanToArabic() {
        withDelay(
            context = Dispatchers.Default,
            mills = INPUT_DELAY,
        ) {
            try {
                val arabic = converter.romanToInt(it).toString()
                updateArabicInput(arabic, false)
            } catch (e: IllegalArgumentException) {
                Log.e(this@ConvertViewModel::class.simpleName, e.stackTraceToString())
            }
        }
    }

    private suspend inline fun String.arabicToRoman() {
        withDelay(
            context = Dispatchers.Default,
            mills = INPUT_DELAY,
        ) {
            try {
                val roman = converter.intToRoman(it.toInt())
                updateRomanInput(roman, false)
            } catch (e: NumberFormatException) {
                Log.e(this@ConvertViewModel::class.simpleName, e.stackTraceToString())
            }
        }
    }
}