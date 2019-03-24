package com.jahnold.lastfmsample.base.data.domain

enum class ImageSize {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE;

    companion object {

        fun fromString(input: String): ImageSize {

            return when(input) {
                "small" -> SMALL
                "medium" -> MEDIUM
                "large" -> LARGE
                "extralarge" -> EXTRA_LARGE
                else -> throw IllegalArgumentException("Unknown image size $input")
            }
        }
    }
}