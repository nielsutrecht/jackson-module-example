package com.nibado.example.jacksonmodule

class TestFilterSupplier(val typeFields: Set<TypeField>) : FieldFilterSupplier {
    override fun filters() = typeFields
}
