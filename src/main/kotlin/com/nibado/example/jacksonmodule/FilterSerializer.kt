package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import kotlin.reflect.full.memberProperties


class FilterSerializer(private val config: FilterConfig, private val fieldFilterSupplier: FieldFilterSupplier) :
    JsonSerializer<Any>() {
    override fun serialize(value: Any, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()

        value.javaClass.kotlin.memberProperties.forEach {
            val auth = config.authorization(value.javaClass, it.name)
            if (fieldFilterSupplier.filters().contains(auth)) {
                val fieldValue = it.get(value)
                gen.writeFieldName(it.name)
                provider.defaultSerializeValue(fieldValue, gen)
            }
        }
        gen.writeEndObject()
    }
}
