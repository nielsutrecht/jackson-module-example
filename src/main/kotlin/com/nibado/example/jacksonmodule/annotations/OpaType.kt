package com.nibado.example.jacksonmodule.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OpaType(val type: String)
