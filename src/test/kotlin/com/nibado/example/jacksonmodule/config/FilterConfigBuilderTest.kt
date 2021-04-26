package com.nibado.example.jacksonmodule.config

import org.junit.jupiter.api.Test

internal class FilterConfigBuilderTest {
    @Test
    fun `Scanning should find 3 classes`() {
        val classes = FilterConfigBuilder.scan("com.nibado.example.jacksonmodule")

        classes.forEach { println(it) }
    }
}
