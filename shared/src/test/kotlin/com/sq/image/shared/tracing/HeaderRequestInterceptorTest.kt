package com.sq.image.shared.tracing

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HeaderRequestInterceptorTest {

    private val requestInterceptor = HeaderRequestInterceptor()

    private val request = mockk<HttpServletRequest>()
    private val response = mockk<HttpServletResponse>()

    @Test
    fun `should forward all tracing header information`() {
        every { request.getHeader(any()) } returns "foo"

        requestInterceptor.preHandle(request, response, Object())

        val numberOfTracingHeaders = requestInterceptor.tracingHeader.size
        verify(exactly = numberOfTracingHeaders) { response.setHeader(any(), any()) }
    }

    @Test
    fun `should forward only tracing headers which exists`() {
        every { request.getHeader("end-user") } returns "foo"
        every { request.getHeader("x-request-id") } returns "4711"

        requestInterceptor.preHandle(request, response, Object())

        verify { response.setHeader("end-user", "foo") }
        verify { response.setHeader("x-request-id", "4711") }

        verify(exactly = 2) { response.setHeader(any(), any()) }
    }

    @Test
    fun `should ignore non tracing headers`() {

        every { request.getHeader("content") } returns "foo"

        requestInterceptor.preHandle(request, response, Object())

        verify(exactly = 0) { response.setHeader(any(), any()) }
    }

}