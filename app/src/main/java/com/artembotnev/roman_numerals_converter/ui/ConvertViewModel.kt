package com.artembotnev.roman_numerals_converter.ui

import RomanNumeralConverter
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

private const val INPUT_DELAY = 350L

@FlowPreview
class ConvertViewModel : ViewModel() {

    private val converter = RomanNumeralConverter()

    private val _viewState: MutableStateFlow<ConverterViewState> by lazy {
        MutableStateFlow(
            ConverterViewState(
                romanText = "",
                arabicText = "",
                romanInputError = false,
                arabicInputError = false
            )
        )
    }

    private val arabicInputFlow = MutableSharedFlow<String>()
    private val romanInputFlow = MutableSharedFlow<String>()

    val viewState: StateFlow<ConverterViewState> = _viewState

    init {
        with(viewModelScope) {
            launch { arabicInputFlow.debounce(INPUT_DELAY).collectLatest { it.arabicToRoman() } }
            launch { romanInputFlow.debounce(INPUT_DELAY).collectLatest { it.romanToArabic() } }
        }
    }

    fun updateRomanInput(text: String, isInputUpdate: Boolean = true) {
        validateRomanInput(text)
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(romanText = text)
            )
            if (isInputUpdate) romanInputFlow.emit(text)
        }
    }

    fun updateArabicInput(text: String, isInputUpdate: Boolean = true) {
        validateArabicInput(text)
        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(arabicText = text)
            )
            if (isInputUpdate) arabicInputFlow.emit(text)
        }
    }

    private fun validateRomanInput(text: String) {
        val notRomanNumeralSymbol = text.contains(regex = Regex("[^MCDXLVI]"))

        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(romanInputError = notRomanNumeralSymbol)
            )
        }
    }

    private fun validateArabicInput(text: String) {
        val notNumeralSymbol = text.contains(regex = Regex("[^0-9]"))

        viewModelScope.launch {
            _viewState.emit(
                _viewState.value.copy(arabicInputError = notNumeralSymbol)
            )
        }
    }

    private fun String.romanToArabic() {
        if (isEmpty()) updateArabicInput("", false)
        try {
            val arabic = converter.romanToInt(this).toString()
            updateArabicInput(arabic, false)
        } catch (e: IllegalArgumentException) {
            Log.e(this@ConvertViewModel::class.simpleName, e.stackTraceToString())
        }
    }

    private fun String.arabicToRoman() {
        if (isEmpty()) updateRomanInput("", false)
        try {
            val roman = converter.intToRoman(toInt())
            updateRomanInput(roman, false)
        } catch (e: NumberFormatException) {
            Log.e(this@ConvertViewModel::class.simpleName, e.stackTraceToString())
        }
    }
}