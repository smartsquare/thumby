package com.sq.image.upload.thumbnail

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.nio.charset.StandardCharsets


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockKExtension::class)
internal class ThumbnailStatusControllerTest {

    val restClient = mockk<RestTemplate>()

    val underTest = ThumbnailStatusController("server.host", "4711", restClient)
    val mockMvc = MockMvcBuilders.standaloneSetup(underTest)
            .setControllerAdvice(RestExceptionHandler())
            .build()

    @Test
    fun `should map default exceptions to internal server error and set an error http header`() {

        val response = mockMvc.perform(get("/generator-status"))
                .andDo(MockMvcResultHandlers.print())

        response.andExpect(status().isInternalServerError)
                .andExpect(header().exists("error"))
                .andExpect(content().string(`is`(not(emptyString()))))

    }

    @Test
    fun `should map client side exception to internal server error and pass downstream failure information`() {

        val responseException = RestClientResponseException("message", 404, "statusText", HttpHeaders(), "".toByteArray(), StandardCharsets.UTF_8)

        every { restClient.getForEntity(any<URI>(), any<Class<String>>()) } throws responseException


        val response = mockMvc.perform(get("/generator-status"))
                .andDo(MockMvcResultHandlers.print())

        response.andExpect(status().isInternalServerError)
                .andExpect(content().string(`is`(not(emptyString()))))
                .andExpect(jsonPath("$.url", `is`("http://localhost/generator-status")))
                .andExpect(jsonPath("$.downstreamExceptionName", `is`(responseException.javaClass.simpleName)))
                .andExpect(jsonPath("$.downstreamErrorMessage", `is`(responseException.message)))
                .andExpect(jsonPath("$.downstreamErrorCode", `is`(responseException.rawStatusCode)))
    }

    @Test
    fun `should map server side exception to internal server error and pass downstream failure information`() {
        val responseException = HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable", HttpHeaders(), "Failure".toByteArray(), StandardCharsets.UTF_8)

        every { restClient.getForEntity(any<URI>(), any<Class<String>>()) } throws responseException

        val response = mockMvc.perform(get("/generator-status"))
                .andDo(MockMvcResultHandlers.print())

        response.andExpect(status().isInternalServerError)
                .andExpect(content().string(`is`(not(emptyString()))))
                .andExpect(jsonPath("$.downstreamExceptionName", `is`(responseException.javaClass.simpleName)))
                .andExpect(jsonPath("$.url", `is`("http://localhost/generator-status")))
                .andExpect(jsonPath("$.downstreamErrorMessage", `is`(responseException.message)))
                .andExpect(jsonPath("$.downstreamErrorCode", `is`(responseException.rawStatusCode)))
    }
}