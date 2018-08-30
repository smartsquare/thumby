package com.sq.image.shared.tracing

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HeaderRequestInterceptor : HandlerInterceptorAdapter() {

    val tracingHeader = listOf("end-user", "x-request-id",
            "x-b3-traceid", "x-b3-spanid",
            "x-b3-parentspanid", "x-b3-sampled",
            "x-b3-flags", "x-ot-span-context")

    override fun preHandle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any
    ): Boolean {

        tracingHeader.forEach {
            setHeaderIfNotEmpty(response, it, request.getHeader(it).orEmpty())
        }

        return true
    }

    private fun setHeaderIfNotEmpty(response: HttpServletResponse, headerName: String, headerValue: String) {
        if (headerValue.isNotEmpty()) {
            response.setHeader(headerName, headerValue)
        }
    }
}
