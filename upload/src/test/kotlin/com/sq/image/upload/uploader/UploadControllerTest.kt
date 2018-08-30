package com.sq.image.upload.uploader

import com.sq.image.upload.thumbnail.ThumbnailService
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UploadControllerTest {

    private val uploadService = mockk<UploadService>()

    private val thumbnailService = mockk<ThumbnailService>()

    private val underTest = UploadController(uploadService, thumbnailService)
    private val mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()

    private lateinit var file: MockMultipartFile

    @BeforeEach
    internal fun setUp() {
        file = MockMultipartFile("file", "image.jpg", null, "bar".toByteArray())
    }

    @Test
    fun `should return index page`() {
        mockMvc.perform(get("/")).andExpect(status().isOk)
    }

    @Test
    fun `should upload an image and trigger thumbnail generator`() {
        every { uploadService.upload(any()) } returns "fileLink"
        every { thumbnailService.createThumbnails(any(), any(), any(), any(), any()) } returns Unit

        val result = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(file))

        result.andExpect(status().is3xxRedirection)
                .andExpect(flash().attributeExists<String>("message"))
    }

    @Test
    fun `should handle exceptions during upload process`() {
        val errorMessage = "Error during upload"
        every { uploadService.upload(any()) } throws Exception(errorMessage)

        val result = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(file))

        result.andExpect(status().is3xxRedirection)
                .andExpect(flash().attributeExists<String>("message"))
                .andExpect(flash().attribute("message", containsString(errorMessage)))
    }
}
