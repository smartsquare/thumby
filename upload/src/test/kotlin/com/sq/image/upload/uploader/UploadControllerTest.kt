package com.sq.image.upload.uploader

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UploadControllerTest {

    private val uploadService = mockk<UploadService>()

    private val underTest = UploadController(uploadService)
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

        val result = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(file))

        result.andExpect(status().is2xxSuccessful)
                .andExpect(content().string(file.originalFilename))
    }

    @Test
    fun `should handle exceptions during upload process`() {
        val errorMessage = "Error during upload"
        every { uploadService.upload(any()) } throws Exception(errorMessage)

        val result = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(file))
                .andDo(print())

        result.andExpect(status().is5xxServerError)
                .andExpect(content().string("upload failed"))
    }
}
