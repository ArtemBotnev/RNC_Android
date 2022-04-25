package com.artembotnev.roman_numerals_converter

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> T.withDelay(context: CoroutineContext, mills: Long, action: (T) -> Unit) {
    withContext(context) {
        delay(mills)
        action(this@withDelay)
    }
}