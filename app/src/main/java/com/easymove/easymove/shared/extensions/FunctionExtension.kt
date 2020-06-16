package com.easymove.easymove.shared.extensions

//fun <T> debounce(
//    delayMs: Long = 500L,
//    coroutineContext: CoroutineContext,
//    f: (T) -> Unit
//): (T) -> Unit {
//    var debounceJob: Job? = null
//
//    return { param: T ->
//        if (debounceJob?.isCompleted != false) {
//            debounceJob = CoroutineScope(coroutineContext).launch {
//                delay(delayMs)
//                f(param)
//            }
//        }
//    }
//}
