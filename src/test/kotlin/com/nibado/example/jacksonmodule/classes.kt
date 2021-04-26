package com.nibado.example.jacksonmodule

import com.nibado.example.jacksonmodule.annotations.OpaField
import com.nibado.example.jacksonmodule.annotations.OpaType

@OpaType("Quote")
data class Quote(
    @OpaField("id") val id: Int,
    @OpaField("author") val author: Author,
    @OpaField("quote") val quote: String)

@OpaType("Quote")
data class QuoteV2(
    @OpaField("id") val id: Int,
    @OpaField("author") val author: Author,
    @OpaField("quote") val quote: String,
    @OpaField("year") val year: Int)

@OpaType("Author")
data class Author(
    @OpaField("id") val id: Int,
    @OpaField("name") val name: String)
