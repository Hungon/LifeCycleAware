package com.trials.life_cycle_aware

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

abstract class MyLifeCycle {

    abstract fun addObserver(observer: LifecycleObserver)

    abstract  fun removeObserver(observer: LifecycleObserver)

    abstract  fun getCurrentState(): State

    enum class State {}

    enum class Event {
        /**
         * Constant for say.
         */
        ON_SAY,
        /**
         * Constant for animate.
         */
        ON_ANIMATE,
        /**
         * An [Event] constant that can be used to match all events.
         */
        ON_ANY
    }

}