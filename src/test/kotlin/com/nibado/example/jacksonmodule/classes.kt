package com.nibado.example.jacksonmodule

import com.nibado.example.jacksonmodule.annotations.OpaField
import com.nibado.example.jacksonmodule.annotations.OpaType

@OpaType("Quote")
data class Quote(
    @OpaField val id: Int,
    @OpaField val author: Author,
    @OpaField val quote: String)

@OpaType("Quote")
data class QuoteV2(
    @OpaField val id: Int,
    @OpaField val author: Author,
    @OpaField val quote: String,
    @OpaField val year: Int)

@OpaType("Author")
data class Author(
    @OpaField val id: Int,
    @OpaField val name: String)
