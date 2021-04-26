package com.nibado.example.jacksonmodule

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

class RequestContextFilterSupplier(private val config: FilterConfig) : PropertyFilterSupplier {
    override fun filters(): List<PropertyFilter>  = parseHeader(getHttpRequest())

    private fun parseHeader(request: HttpServletRequest) : List<PropertyFilter> {
        val header = request.getHeader(HEADER_NAME)
            ?: if(config.failOnMissingHeader) {
                throw IllegalStateException("Missing $HEADER_NAME header")
            } else {
                return emptyList()
            }

        return header.split(FILTER_SEPARATOR)
            .map(::parseFilter)
    }

    private fun parseFilter(filter: String) : PropertyFilter {
        val matcher = TYPE_PROPERTY_REGEX.matchEntire(filter)
            ?: throw IllegalStateException("Filter $filter does not match ${TYPE_PROPERTY_REGEX.pattern}")

        return matcher.groupValues.let { (type, prop) -> ConfigLookupFilter(config, type, prop) }
    }

    private fun getHttpRequest() : HttpServletRequest {
        val attributes = RequestContextHolder.getRequestAttributes()
            ?: throw IllegalStateException("Called outside Spring request context")

        if(attributes !is ServletRequestAttributes) {
            throw IllegalStateException("Expected ServletRequestAttributes but got ${attributes.javaClass.name} instead")
        }

        return attributes.request
    }

    companion object {
        const val HEADER_NAME = "X-Return-Fields"
        private val FILTER_SEPARATOR = "\\s+".toRegex()
        private val TYPE_PROPERTY_REGEX = "([A-Za-z0-9_]+):([A-Za-z0-9_]+)".toRegex()
    }
}
