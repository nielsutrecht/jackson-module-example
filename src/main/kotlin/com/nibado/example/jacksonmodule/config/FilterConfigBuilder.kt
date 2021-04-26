package com.nibado.example.jacksonmodule.config

import com.nibado.example.jacksonmodule.FilterConfig
import com.nibado.example.jacksonmodule.annotations.OpaField
import com.nibado.example.jacksonmodule.annotations.OpaType
import org.reflections.ReflectionUtils.getAllFields
import org.reflections.ReflectionUtils.withAnnotation
import org.reflections.Reflections
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

class FilterConfigBuilder {
    private var failOnMissingHeader = true
    private val mappings = mutableMapOf<FilterConfig.ClassProperty, FilterConfig.PropertyAuthorization>()

    fun withFailOnMissingHeader(value: Boolean) : FilterConfigBuilder {
        failOnMissingHeader = value
        return this
    }

    fun withScanForMappings(basePackage: String) : FilterConfigBuilder {
        mappings += scan(basePackage)
        return this
    }

    fun with(clazz: Class<*>, field: String, opaType: String, opaField: String) : FilterConfigBuilder {
        mappings[FilterConfig.ClassProperty(clazz, field)] = FilterConfig.PropertyAuthorization(opaType, opaField)

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
        fun scan(basePackage: String) : Map<FilterConfig.ClassProperty, FilterConfig.PropertyAuthorization> {
            val reflections = Reflections(basePackage)

            val annotatedClasses = reflections.getTypesAnnotatedWith(OpaType::class.java)

            return annotatedClasses.flatMap { c ->
                val type = c.getAnnotation(OpaType::class.java).type

                val fields = getAllFields(c, withAnnotation(OpaField::class.java))

                fields
                    .map { field ->
                        val fieldName = field.getAnnotation(OpaField::class.java).field.let { if(it == "[default]") field.name else it}
                        FilterConfig.ClassProperty(c, field.name) to FilterConfig.PropertyAuthorization(type, fieldName)
                    }
            }.toMap()
        }
    }
}
