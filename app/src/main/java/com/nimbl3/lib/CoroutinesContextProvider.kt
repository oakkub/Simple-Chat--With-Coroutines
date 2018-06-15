package com.nimbl3.lib

import kotlin.coroutines.experimental.CoroutineContext

interface CoroutinesContextProvider {

    val ui: CoroutineContext

    val bg: CoroutineContext

}