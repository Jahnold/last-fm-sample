package com.jahnold.lastfmsample.base.transformers

interface Transformer<I, O> {
    fun transform(input: I): O
}