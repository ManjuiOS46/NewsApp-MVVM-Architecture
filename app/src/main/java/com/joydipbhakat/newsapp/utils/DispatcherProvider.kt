package com.joydipbhakat.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val dispatcherMain: CoroutineDispatcher
    val dispatcherIO: CoroutineDispatcher
    val dispatcherDefault: CoroutineDispatcher
}

class DefaultDispatcher : DispatcherProvider {
    override val dispatcherMain: CoroutineDispatcher
        get() = Dispatchers.Main
    override val dispatcherIO: CoroutineDispatcher
        get() = Dispatchers.IO
    override val dispatcherDefault: CoroutineDispatcher
        get() = Dispatchers.Default

}