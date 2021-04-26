package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier

class FilterPropertyModifier(private val propertyFilterSupplier: PropertyFilterSupplier) : BeanSerializerModifier() {
    override fun changeProperties(
        config: SerializationConfig,
        beanDesc: BeanDescription,
        beanProperties: List<BeanPropertyWriter>
    ): List<BeanPropertyWriter> {

        return beanProperties.filter { includeProperty(it) }
    }

    private fun includeProperty(property: BeanPropertyWriter) : Boolean {
        val relevantFilters = propertyFilterSupplier.filters().filter { it.applies(property) }

        return relevantFilters.all { it.allowed(property) }
    }
}
