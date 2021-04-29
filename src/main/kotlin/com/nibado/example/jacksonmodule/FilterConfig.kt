package com.nibado.example.jacksonmodule

class FilterConfig(
    val failOnMissingHeader: Boolean, val lookup: Map<ClassField, TypeField>) {

    data class ClassField(val clazz: Class<*>, val field: String)

    fun authorization(clazz: Class<*>, field: String) : TypeField? =
        lookup[ClassField(clazz, field)]
}
