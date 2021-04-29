package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.ser.Serializers
import com.nibado.example.jacksonmodule.annotations.OpaType
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class FilterSerializers(private val config: FilterConfig, private val fieldFilterSupplier: FieldFilterSupplier) :
    Serializers.Base() {
    override fun findSerializer(
        config: SerializationConfig,
        type: JavaType,
        beanDesc: BeanDescription
    ): JsonSerializer<*>? =
        if (type.rawClass.annotations.any { it.annotationClass == OpaType::class }) {
            log.debug { "Mapping ${type.rawClass} to FilterSerializer" }
            FilterSerializer(this.config, fieldFilterSupplier)
        } else {
            null
        }
}
