package com.jahnold.lastfmsample.base.data

enum class ImageSize {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE,
    MEGA;

    companion object {

        fun fromString(input: String?): ImageSize?{

            return when(input) {
                "small" -> SMALL
                "medium" -> MEDIUM
                "large" -> LARGE
                "extralarge" -> EXTRA_LARGE
                "mega" -> MEGA
                else -> null
            }
        }
    }
}