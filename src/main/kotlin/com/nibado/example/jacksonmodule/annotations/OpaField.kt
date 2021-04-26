package com.nibado.example.jacksonmodule.annotations

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class OpaField(val field: String = "[default]")
