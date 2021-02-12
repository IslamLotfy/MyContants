package com.example.mycontants.utils

fun <T> MutableList<T>.replace(newValue: T, block: (T) -> Boolean): MutableList<T> {
    return map {
        if (block(it)) newValue else it
    } as MutableList<T>
}
