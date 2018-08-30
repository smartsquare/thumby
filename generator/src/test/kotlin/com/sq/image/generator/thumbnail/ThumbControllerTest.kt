package com.sq.image.generator.thumbnail

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThumbControllerTest {

    private val thumbGenerator = mockk<ThumbGenerator>(relaxed = true)

    private val underTest = ThumbController(thumbGenerator)
    private val mockMvc = MockMvcBuilders.standaloneSetup(underTest).build()

    @Test
    fun `should call thumb service on request`() {
        val width = "120"
        val height = "240"
        val uri = URI("http://local:host/img/image.jpg/thumb/$width/$height")

        mockMvc.perform(post(uri)).andExpect(status().isOk)

        verify { thumbGenerator.createThumb(eq("image.jpg"), eq(width.toInt()), eq(height.toInt())) }
    }
}
