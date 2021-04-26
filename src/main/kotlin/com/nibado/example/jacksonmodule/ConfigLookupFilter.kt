package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter

class ConfigLookupFilter(private val config: FilterConfig, private val type: String, private val property: String) : PropertyFilter {
    override fun applies(property: BeanPropertyWriter): Boolean =
        config.lookup.containsKey(property.classProperty())

    override fun allowed(property: BeanPropertyWriter): Boolean {
        val auth = config.lookup.getValue(property.classProperty())
        return auth.type == type && auth.property == this.property
    }

    private fun BeanPropertyWriter.classProperty() = FilterConfig.ClassProperty(fullName.javaClass, fullName.simpleName)
}
