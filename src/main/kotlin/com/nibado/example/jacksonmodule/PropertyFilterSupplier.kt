package com.nibado.example.jacksonmodule

interface PropertyFilterSupplier {
    fun filters() : List<PropertyFilter>
}
