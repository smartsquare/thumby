package com.sq.image.upload.thumbnail

import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestClientResponseException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice("com.sq.image.upload.thumbnail")
class RestExceptionHandler {

    private val log = LogManager.getLogger()

    @ExceptionHandler(Exception::class)
    fun handleUnkownError(req: HttpServletRequest, e: Exception): ResponseEntity<HttpErrorInfo> {
        val body = "Unkown Error [${e.javaClass.simpleName}] while connection to url: '${req.requestURL}' and error message: '${e.message}'"
        log.error(body)

        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8")
        headers.add("error", "true")

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .body(HttpErrorInfo(req.requestURL, e.javaClass.simpleName, e.message!!))
    }

    @ExceptionHandler(RestClientResponseException::class)
    fun handleClientError(req: HttpServletRequest, e: RestClientResponseException): ResponseEntity<HttpRestErrorInfo> {
        log.error("Client Error while connection to generator service. Remote Server responses with HttpStatus [${e.rawStatusCode}]")

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpRestErrorInfo(req.requestURL, e.javaClass.simpleName, e.message!!, e.rawStatusCode))
    }
}

data class HttpRestErrorInfo(
        val url: StringBuffer,
        val downstreamExceptionName: String,
        val downstreamErrorMessage: String,
        val downstreamErrorCode: Int
)

data class HttpErrorInfo(
        val url: StringBuffer,
        val exceptionName: String,
        val errorMessage: String
)
