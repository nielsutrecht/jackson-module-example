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
        val lookup = mapOf(
            FilterConfig.ClassProperty(Quote::class.java,"id") to FilterConfig.PropertyAuthorization("Quote", "id"),
            FilterConfig.ClassProperty(Author::class.java,"id") to FilterConfig.PropertyAuthorization("Author", "id")
        )
        return ObjectMapper().registerKotlinModule()
            .registerModule(FilterModule(FilterConfig(true, lookup), TestFilterSupplier()))
    }
}
