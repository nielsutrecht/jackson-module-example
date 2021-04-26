package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Test

internal class FilterModuleTest {

    @Test
    fun test() {
        val mapper = mapper()
        val testClass = Quote(1, Author(123, "Bruce Lee"), "Be water my friend")

        println(mapper.writeValueAsString(testClass))
    }

    private fun mapper() : ObjectMapper {
        return ObjectMapper().registerKotlinModule()
            .registerModule(FilterModule(FilterConfig(setOf())))
    }

    private data class Quote(val id: Int, val author: Author, val quote: String)
    private data class Author(val id: Int, val name: String)
}
