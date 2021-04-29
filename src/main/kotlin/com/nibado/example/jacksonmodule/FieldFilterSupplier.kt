package com.nibado.example.jacksonmodule

interface FieldFilterSupplier {
    fun filters() : Set<TypeField>
}
