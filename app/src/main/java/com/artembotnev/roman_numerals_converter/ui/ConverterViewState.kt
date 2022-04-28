package com.artembotnev.roman_numerals_converter.ui

data class ConverterViewState(
    val romanText: String,
    val arabicText: String,
    val romanInputError: Boolean,
    val arabicInputError: Boolean,
)