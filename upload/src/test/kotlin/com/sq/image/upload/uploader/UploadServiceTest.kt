package com.sq.image.upload.uploader

import com.sq.image.shared.storage.GCloudStorageAdapter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.mock.web.MockMultipartFile

internal class UploadServiceTest {

    private val cloudAdapter = mockk<GCloudStorageAdapter>()

    @Test
    fun `should upload image file to cloud storage`() {
        val returnImageLink = "http://link_to_image.jpg"
        every { cloudAdapter.upload(any(), any()) } returns returnImageLink

        val fileContent = "bar".toByteArray()
        val imageFileName = "image.jpg"

        val file = MockMultipartFile("file", imageFileName, null, fileContent)

        val service = UploadService(cloudAdapter)

        val imageLink = service.upload(file)

        assertThat(imageLink).isEqualTo(returnImageLink)
        verify { cloudAdapter.upload(eq(imageFileName), eq(fileContent)) }
    }

    @Test
    fun `should throw exception if filename is empty`() {
        val returnImageLink = "http://link_to_image.jpg"
        every { cloudAdapter.upload(any(), any()) } returns returnImageLink

        val fileContent = "bar".toByteArray()
        val file = MockMultipartFile("file", null, null, fileContent)

        val service = UploadService(cloudAdapter)
        assertThrows<IllegalArgumentException> { service.upload(file) }
    }
}