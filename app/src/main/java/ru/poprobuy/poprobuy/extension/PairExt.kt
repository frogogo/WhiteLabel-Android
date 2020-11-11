package ru.poprobuy.poprobuy.extension

@Suppress("FunctionMinLength")
infix fun <A, B, C> Pair<A, B>.to(third: C): Triple<A, B, C> = Triple(first, second, third)
