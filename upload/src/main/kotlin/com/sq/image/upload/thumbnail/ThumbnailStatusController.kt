package com.sq.image.upload.thumbnail

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.servlet.http.HttpServletResponse

@RestController
class ThumbnailStatusController(
        @Value("\${thumbnail-service.hostname}") val thumbnailHost: String,
        @Value("\${thumbnail-service.port}") val thumbnailPort: String,
        val restClient: RestTemplate
) {

    private val log = LogManager.getLogger()

    @GetMapping("/generator-status")
    @ResponseBody
    fun generatorUrl(response: HttpServletResponse): ResponseEntity<String> {

        log.info("requesting generator service info.")

        val uri: URI = UriComponentsBuilder
                .fromUriString("http://$thumbnailHost:$thumbnailPort/info")
                .build()
                .toUri()

        // x-envoy-upstream-rq-timeout-ms and x-envoy-max-retries
        val responseEntity = restClient.getForEntity(uri, String::class.java)
        log.debug("Response from generator service: ${responseEntity.statusCodeValue} ${responseEntity.body} ${responseEntity.headers}")

        return responseEntity
    }
}
