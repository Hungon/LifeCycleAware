package com.trials.life_cycle_aware

import androidx.lifecycle.Lifecycle
import java.lang.annotation.RetentionPolicy


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class OnMyLifeCycleEvent(val value: MyLifeCycle.Event)
