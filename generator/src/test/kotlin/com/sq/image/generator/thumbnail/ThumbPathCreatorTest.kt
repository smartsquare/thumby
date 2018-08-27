package com.sq.image.generator.thumbnail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files

class ThumbPathCreatorTest {

    private lateinit var creator: ThumbPathCreator

    @BeforeEach
    fun setUp() {
        creator = ThumbPathCreator()
    }

    @Test
    fun `should determine new image name`() {

        val tempFile = Files.createTempFile("image-", ".jpg")

        val width = 120
        val height = 240

        val newImagePath = creator.createNewImagePath(tempFile, "image.jpg", width, height)

        val expectedImageName = "image-${width}x${height}.jpg"

        assertThat(newImagePath.toString()).endsWith(expectedImageName)

    }
}