package com.nibado.example.jacksonmodule

import com.fasterxml.jackson.databind.ser.BeanPropertyWriter

class AllowAllFilter : PropertyFilter {
    override fun applies(property: BeanPropertyWriter): Boolean = true
    override fun allowed(property: BeanPropertyWriter): Boolean = true
}
