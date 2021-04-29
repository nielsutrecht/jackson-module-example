package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.introspect.AnnotatedMember
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
import com.nibado.example.jacksonmodule.annotations.OpaField
import com.nibado.example.jacksonmodule.annotations.OpaType
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class FilterAnnotationIntrospector(private val config: FilterConfig, private val fieldFilterSupplier: FieldFilterSupplier) : JacksonAnnotationIntrospector() {
    override fun hasIgnoreMarker(m: AnnotatedMember): Boolean {
        if(!hasAnnotations(m)) {
            return super.hasIgnoreMarker(m)
        }

        log.debug { m.toString() }

        val authorization = config.authorization(m.declaringClass, m.name) ?: return super.hasIgnoreMarker(m)

        log.debug { authorization }

        val isAllowed = fieldFilterSupplier.filters().contains(authorization)

        log.debug { "$authorization: $isAllowed" }

        return !isAllowed
    }

    private fun hasAnnotations(m: AnnotatedMember) : Boolean =
        m.declaringClass.getAnnotation(OpaType::class.java) != null && m.allAnnotations.has(OpaField::class.java)
}
