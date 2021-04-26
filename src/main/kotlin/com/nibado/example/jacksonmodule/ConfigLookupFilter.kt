package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter

class ConfigLookupFilter(private val config: FilterConfig, val type: String, val property: String) : PropertyFilter {
    override fun allowed(property: BeanPropertyWriter): Boolean {
        TODO("Not yet implemented")
    }
}
