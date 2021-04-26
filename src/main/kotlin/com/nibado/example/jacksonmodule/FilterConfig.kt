package com.nibado.example.jacksonmodule

class FilterConfig(
    val failOnMissingHeader: Boolean, val lookup: Map<ClassProperty, PropertyAuthorization>) {


    data class ClassProperty(val clazz: Class<*>, val property: String)

    data class PropertyAuthorization(val type: String, val property: String)
}
