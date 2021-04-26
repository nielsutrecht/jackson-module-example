package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module

class FilterModule(private val config: FilterConfig) : Module() {
    override fun version(): Version = VERSION

    override fun getModuleName() : String = NAME

    override fun setupModule(context: SetupContext) {
        context.addBeanSerializerModifier(FilterPropertyModifier())
    }

    companion object {
        private val VERSION = Version(
            1, 0, 0, null,
            "com.nibado.example.jackson-module",
            "jackson-module-example"
        )

        private const val NAME = "filter-module"
    }
}
