package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nibado.example.jacksonmodule.config.FilterConfigBuilder
import org.junit.jupiter.api.Test

internal class FilterModuleTest {

    @Test
    fun test() {

        val testClass = Quote(1, Author(123, "Bruce Lee"), "Be water my friend")

        val config = FilterConfigBuilder().withScanForMappings("com.nibado.example.jacksonmodule")
            .build()

        val filter = mutableSetOf(TypeField("Quote", "quote"), TypeField("Quote", "author"))
        val mapper = mapper(config, filter)

        println(mapper.writeValueAsString(testClass))

        filter += TypeField("Author", "name")

        println(mapper.writeValueAsString(testClass))
    }

    private fun mapper(config: FilterConfig, typeFields: Set<TypeField>): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
            .registerModule(
                FilterModule(config, TestFilterSupplier(typeFields))
            )
    }
}
