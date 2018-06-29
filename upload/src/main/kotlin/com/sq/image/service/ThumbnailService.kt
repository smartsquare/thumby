package com.sq.image.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class ThumbnailService(@Value("\${thumbnail-service.hostname}") val thumbnailHost: String,
                       @Value("\${thumbnail-service.port}") val thumbnailPort: String) {

    private val restTemplate = RestTemplate()

    enum class ScaleType(val urlPart: String) {
        RESIZE("resize"),
        THUMBNAIL("thumb"),
    }

    class Size(val width: Int, val height: Int)

    fun createThumbnail(filename: String, type: ScaleType, size: Size) {
        val url = UriComponentsBuilder.fromHttpUrl("http://{host}:{port}/img/{filename}/{type}/{width}/{height}")
                .buildAndExpand(
                        thumbnailHost,
                        thumbnailPort,
                        filename,
                        type.urlPart,
                        size.width,
                        size.height
                ).toUri()

        restTemplate.postForLocation(
                url,
                null
        )
    }
}