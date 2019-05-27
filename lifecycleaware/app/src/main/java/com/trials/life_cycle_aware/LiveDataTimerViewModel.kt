package com.trials.life_cycle_aware

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.SystemClock

import java.util.Timer
import java.util.TimerTask

/**
 * A ViewModel used for the [MainActivity].
 */
class LiveDataTimerViewModel : ViewModel() {

    val elapsedTime = MutableLiveData<Long>()

    private val initialTime = SystemClock.elapsedRealtime()

    init {
        val timer = Timer()

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                // setValue() cannot be called from a background thread so post to main thread.
                elapsedTime.postValue(newValue)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())

    }

    companion object {

        private val ONE_SECOND = 1000
    }
}
