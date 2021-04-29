package com.nibado.example.jacksonmodule

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.servlet.http.HttpServletRequest

internal class RequestContextFilterSupplierTest {
    @Test
    fun `Missing header should throw exception when configured to do so`() {
        val filterConfig = FilterConfig(true, emptyMap())

        val supplier = RequestContextFilterSupplier(filterConfig)

        val ex = assertThrows<IllegalStateException> {
            supplier.parseHeader(mockRequest())
        }
        assertThat(ex.message).isEqualTo("Missing X-Return-Fields header")
    }

    @Test
    fun `Missing header should not throw exception when configured to not do so`() {
        val filterConfig = FilterConfig(false, emptyMap())

        val supplier = RequestContextFilterSupplier(filterConfig)

        assertThat(supplier.parseHeader(mockRequest())).isEmpty()
    }

    @Test
    fun `Should return allow all filter with header 'all'`() {
        val filterConfig = FilterConfig(true, emptyMap())

        val supplier = RequestContextFilterSupplier(filterConfig)

        val result = supplier.parseHeader(mockRequest("all"))

        assertThat(result).isEmpty()
    }

    @Test
    fun `Should parse header into property filters`() {
        val filterConfig = FilterConfig(true, emptyMap())

        val supplier = RequestContextFilterSupplier(filterConfig)

        val result = supplier.parseHeader(mockRequest("Quote:author Quote:quote Author:name"))

        assertThat(result).hasSize(3)
        assertThat(result).extracting("type").containsExactly("Quote", "Quote", "Author")
        assertThat(result).extracting("property").containsExactly("author", "quote", "name")
    }

    private fun mockRequest(header: String? = null) : HttpServletRequest {
        val request = mock<HttpServletRequest>()
        whenever(request.getHeader(RequestContextFilterSupplier.HEADER_NAME)).thenReturn(header)

        return request
    }
}
