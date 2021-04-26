package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier

class FilterPropertyModifier : BeanSerializerModifier() {
    override fun changeProperties(
        config: SerializationConfig,
        beanDesc: BeanDescription,
        beanProperties: List<BeanPropertyWriter>
    ): List<BeanPropertyWriter> {

        return beanProperties.filter { it.fullName.simpleName != "id" }
    }
}
