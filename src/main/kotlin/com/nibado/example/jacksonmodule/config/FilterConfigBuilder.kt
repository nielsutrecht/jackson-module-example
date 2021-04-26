package com.nibado.example.jacksonmodule.config

import com.nibado.example.jacksonmodule.FilterConfig
import com.nibado.example.jacksonmodule.annotations.OpaField
import com.nibado.example.jacksonmodule.annotations.OpaType
import org.reflections.Reflections

class FilterConfigBuilder {
    companion object {
        fun scan(basePackage: String) : Map<FilterConfig.ClassProperty, FilterConfig.PropertyAuthorization> {
            val reflections = Reflections(basePackage)

            val annotatedClasses = reflections.getTypesAnnotatedWith(OpaType::class.java)

            return annotatedClasses.flatMap { c ->
                val type = c.getAnnotation(OpaType::class.java).type

                c.declaredFields.filter { it.getAnnotation(OpaField::class.java) != null }
                    .map {
                        val field = it.getAnnotation(OpaField::class.java).field
                        FilterConfig.ClassProperty(c, it.name) to FilterConfig.PropertyAuthorization(type, field)
                    }
                    .toList()
            }.toMap()
        }
    }
}
