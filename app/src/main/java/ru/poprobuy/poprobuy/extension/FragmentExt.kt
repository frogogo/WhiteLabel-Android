package ru.poprobuy.poprobuy.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

val Fragment.appCompatActivity get() = activity as? AppCompatActivity
