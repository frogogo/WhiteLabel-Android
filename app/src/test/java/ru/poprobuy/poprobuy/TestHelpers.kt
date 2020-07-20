package ru.poprobuy.poprobuy

import androidx.lifecycle.Observer
import io.mockk.mockk

fun <T> mockkObserver() = mockk<Observer<T>>(relaxed = true)
