package com.sq.image.upload.thumbnail

import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate
import java.net.URI

internal class ThumbnailServiceTest {

    private val restClient = mockk<RestTemplate>(relaxed = true)

    private lateinit var service: ThumbnailService

    private val host = "local.host"
    private val port = "4711"
    private val imageName = "image.jpg"

    @BeforeEach
    internal fun setUp() {

        MockKAnnotations.init(this, relaxUnitFun = true)

        service = ThumbnailService(host, port, restClient)
    }

    @Test
    fun `should call rest client for each desired thumbnail size`() {
        service.createThumbnails(imageName, Size(120, 120), Size(240, 240), Size(480, 480))

        verify(exactly = 1) { restClient.postForLocation(eq(getUri(Size(120, 120))), isNull()) }
        verify(exactly = 1) { restClient.postForLocation(eq(getUri(Size(240, 240))), isNull()) }
        verify(exactly = 1) { restClient.postForLocation(eq(getUri(Size(480, 480))), isNull()) }
    }

    private fun getUri(size: Size): URI {
        return URI("http://$host:$port/img/$imageName/${ScaleType.THUMBNAIL.urlPart}/${size.width}/${size.height}")
    }
}
