package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter

interface PropertyFilter {
    fun allowed(property: BeanPropertyWriter) : Boolean
}
