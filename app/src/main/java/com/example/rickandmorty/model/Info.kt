package com.example.rickandmorty.model

import kotlinx.serialization.Serializable


@Serializable
data class Info(
    val count: Int,
    val next: String?=null,
    val pages: Int,
    val prev: String? = null
)