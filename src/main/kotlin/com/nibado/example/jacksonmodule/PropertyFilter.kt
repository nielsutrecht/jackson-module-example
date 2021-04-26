package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter

interface PropertyFilter {
    fun applies(property: BeanPropertyWriter) : Boolean
    fun allowed(property: BeanPropertyWriter) : Boolean
}
