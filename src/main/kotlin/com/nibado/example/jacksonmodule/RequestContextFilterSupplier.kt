package com.nibado.example.jacksonmodule

import mu.KotlinLogging
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

private val log = KotlinLogging.logger {}

class RequestContextFilterSupplier(private val config: FilterConfig) : FieldFilterSupplier {
    override fun filters(): Set<TypeField>  = parseHeader(getHttpRequest())

    internal fun parseHeader(request: HttpServletRequest) : Set<TypeField> {
        val header = request.getHeader(HEADER_NAME)
            ?: if(config.failOnMissingHeader) {
                log.error { "Missing $HEADER_NAME header" }
                throw IllegalStateException("Missing $HEADER_NAME header")
            } else {
                log.debug { "$HEADER_NAME missing so allowing all types and fields." }
                return emptySet()
            }

        log.debug { "$HEADER_NAME: $header" }

        if(header.equals(RETURN_ALL, true)) {
            return emptySet()
        }

        return header.split(FILTER_SEPARATOR)
            .map(::parseFilter).toSet()
    }

    private fun parseFilter(filter: String) : TypeField {
        val matcher = TYPE_PROPERTY_REGEX.matchEntire(filter)
            ?: throw IllegalStateException("Filter $filter does not match ${TYPE_PROPERTY_REGEX.pattern}")

        return matcher.groupValues.let { (_, type, prop) -> TypeField(type, prop) }
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
        private const val RETURN_ALL = "all"
    }
}
