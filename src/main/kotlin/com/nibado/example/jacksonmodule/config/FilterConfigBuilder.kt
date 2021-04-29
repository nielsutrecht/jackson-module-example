package com.nibado.example.jacksonmodule.config

import com.nibado.example.jacksonmodule.FilterConfig
import com.nibado.example.jacksonmodule.TypeField
import com.nibado.example.jacksonmodule.annotations.OpaField
import com.nibado.example.jacksonmodule.annotations.OpaType
import mu.KotlinLogging
import org.reflections.ReflectionUtils.getAllFields
import org.reflections.ReflectionUtils.withAnnotation
import org.reflections.Reflections
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

private val log = KotlinLogging.logger {}

class FilterConfigBuilder {
    private var failOnMissingHeader = true
    private val mappings = mutableMapOf<FilterConfig.ClassField, TypeField>()

    fun withFailOnMissingHeader(value: Boolean) : FilterConfigBuilder {
        failOnMissingHeader = value
        return this
    }

    fun withScanForMappings(basePackage: String) : FilterConfigBuilder {
        mappings += scan(basePackage)
        return this
    }

    fun with(clazz: Class<*>, field: String, opaType: String, opaField: String) : FilterConfigBuilder {
        mappings[FilterConfig.ClassField(clazz, field)] = TypeField(opaType, opaField)

        return this
    }

    fun with(property: KProperty<*>, opaType: String, opaField: String) : FilterConfigBuilder {
        return with(property.javaField!!.declaringClass, property.name, opaType, opaField)
    }

    fun with(opaType: String, vararg properties: KProperty<*>) : FilterConfigBuilder {
        properties.forEach { property ->
            with(property.javaField!!.declaringClass, property.name, opaType, property.name)
        }

        return this
    }

    fun build() : FilterConfig {
        return FilterConfig(failOnMissingHeader, mappings)
    }

    companion object {
        fun scan(basePackage: String) : Map<FilterConfig.ClassField, TypeField> {
            val reflections = Reflections(basePackage)

            val annotatedClasses = reflections.getTypesAnnotatedWith(OpaType::class.java)

            return annotatedClasses.flatMap { c ->
                val type = c.getAnnotation(OpaType::class.java).type

                val fields = getAllFields(c, withAnnotation(OpaField::class.java))

                log.debug { "Found type ${c.name} with fields '${fields.joinToString(", ") { it.name }}'" }

                fields
                    .map { field ->
                        val fieldName = field.getAnnotation(OpaField::class.java).field.let { if(it == "[default]") field.name else it}
                        FilterConfig.ClassField(c, field.name) to TypeField(type, fieldName)
                    }
            }.toMap()
        }
    }
}
