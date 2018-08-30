package com.sq.image.upload.thumbnail

import kotlinx.coroutines.experimental.launch
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class ThumbnailService(
        @Value("\${thumbnail-service.hostname}") private val thumbnailHost: String,
        @Value("\${thumbnail-service.port}") private val thumbnailPort: String,
        private val restClient: RestTemplate
) {

    private val log = LogManager.getLogger()

    fun createThumbnails(originalFilename: String, vararg size: Size) {
        size.forEach {
            launch {
                createThumbnail(originalFilename, ScaleType.THUMBNAIL, it)
            }
        }
    }

    private suspend fun createThumbnail(filename: String, type: ScaleType, size: Size) {

        val url = UriComponentsBuilder.fromHttpUrl("http://{host}:{port}/img/{filename}/{type}/{width}/{height}")
                .buildAndExpand(
                        thumbnailHost,
                        thumbnailPort,
                        filename,
                        type.urlPart,
                        size.width,
                        size.height
                ).toUri()

        log.info("Trigger thumbnail generator: {}", url)

        restClient.postForLocation(url, null)
    }
}
