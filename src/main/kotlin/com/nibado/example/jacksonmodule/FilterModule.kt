package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class FilterModule(private val config: FilterConfig, private val fieldFilterSupplier: FieldFilterSupplier) : Module() {
    override fun version(): Version = VERSION

    override fun getModuleName() : String = NAME

    override fun setupModule(context: SetupContext) {
        //context.addBeanSerializerModifier(FilterPropertyModifier(propertyFilterSupplier))
        context.appendAnnotationIntrospector(FilterAnnotationIntrospector(config, fieldFilterSupplier))
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
