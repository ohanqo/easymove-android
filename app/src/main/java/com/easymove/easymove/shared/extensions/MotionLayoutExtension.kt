package com.easymove.easymove.shared.extensions

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.easymove.easymove.shared.views.MultiListenerMotionLayout
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume

/**
 * Wait for the transition to complete so that the given [transitionId] is fully displayed.
 *
 * @param transitionId The transition set to await the completion of
 * @param timeout Timeout for the transition to take place. Defaults to 5 seconds.
 */
suspend fun MultiListenerMotionLayout.awaitTransitionComplete(
    transitionId: Int,
    timeout: Long = 5000L
) {
    var listener: MotionLayout.TransitionListener? = null

    try {
        withTimeout(timeout) {
            suspendCancellableCoroutine<Unit> { continuation ->
                val l = object : TransitionAdapter() {
                    override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                        if (currentId == transitionId) {
                            removeTransitionListener(this)
                            continuation.resume(Unit)
                        }
                    }
                }
                // If the coroutine is cancelled, remove the listener
                continuation.invokeOnCancellation {
                    removeTransitionListener(l)
                }
                // And finally add the listener
                addTransitionListener(l)
                listener = l
            }
        }
    } catch (tex: TimeoutCancellationException) {
        // Transition didn't happen in time. Remove our listener and throw a cancellation
        // exception to let the coroutine know
        listener?.let(::removeTransitionListener)
        throw CancellationException(
            "Transition to state with id: $transitionId did not" +
                    " complete in timeout.", tex
        )
    }
}