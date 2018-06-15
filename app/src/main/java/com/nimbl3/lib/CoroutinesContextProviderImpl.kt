package com.nimbl3.lib

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

class CoroutinesContextProviderImpl : CoroutinesContextProvider {

    override val ui: CoroutineContext
        get() = UI

    override val bg: CoroutineContext
        get() = CommonPool
}
