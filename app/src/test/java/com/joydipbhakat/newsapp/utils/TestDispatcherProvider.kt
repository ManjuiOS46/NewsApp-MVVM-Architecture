package com.joydipbhakat.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatcherProvider : DispatcherProvider {
    private val testDispatcher  = Dispatchers.Unconfined
    override val dispatcherMain: CoroutineDispatcher
        get() = testDispatcher
    override val dispatcherIO: CoroutineDispatcher
        get() = testDispatcher
    override val dispatcherDefault: CoroutineDispatcher
        get() = testDispatcher
}