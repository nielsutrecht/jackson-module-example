package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nibado.example.jacksonmodule.config.FilterConfigBuilder
import org.junit.jupiter.api.Test

internal class FilterModuleTest {

    @Test
    fun test() {
        val mapper = mapper()
        val testClass = Quote(1, Author(123, "Bruce Lee"), "Be water my friend")

        println(mapper.writeValueAsString(testClass))
    }

    private fun mapper() : ObjectMapper {
        val config = FilterConfigBuilder().withScanForMappings("com.nibado.example.jacksonmodule")
            .with("QuoteNew", Quote::quote, Quote::id, Quote::author)
            .build()

        config.lookup.forEach { println(it) }

        return ObjectMapper().registerKotlinModule()
            .registerModule(FilterModule(config, TestFilterSupplier()))
    }
}
