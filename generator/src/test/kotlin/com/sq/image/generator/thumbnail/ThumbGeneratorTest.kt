package com.sq.image.generator.thumbnail

import com.sq.image.shared.storage.GCloudStorageAdapter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class ThumbGeneratorTest {

    private val cloudAdapter = mockk<GCloudStorageAdapter>(relaxed = true)
    private val thumbnails = mockk<Thumbnails>(relaxed = true)
    private val pathCreator = mockk<ThumbPathCreator>()

    private lateinit var thumbGenerator: ThumbGenerator

    @BeforeEach
    fun setUp() {
        thumbGenerator = ThumbGenerator(cloudAdapter, thumbnails, pathCreator)
    }

    @Test
    fun `should resize image and upload back to cloud storage`() {

        val tempDirectory = Files.createTempDirectory("test")

        every { cloudAdapter.downloadImage(any()) } returns Files.createTempFile(tempDirectory, "image-", ".jpg")
        every { pathCreator.createNewImagePath(any(), any(), any(), any()) } returns Files.createTempFile(tempDirectory, "result-image-", ".jpg")

        val width = 120
        val height = 240

        thumbGenerator.createThumb("image.jpg", width, height)

        verify { thumbnails.createThumbnail(any(), eq(width), eq(height), any()) }
        verify { cloudAdapter.storeImage(any(), any()) }

    }
}