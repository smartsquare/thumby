package com.sq.image.controller

import com.sq.image.service.ThumbnailService
import com.sq.image.service.UploadService
import kotlinx.coroutines.experimental.launch
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class UploadController(val uploadService: UploadService,
                       val thumbnailService: ThumbnailService) {

    val log = LogManager.getLogger()

    @GetMapping("/")
    fun index() = "upload"

    @PostMapping("/upload")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile,
                         redirectAttributes: RedirectAttributes): String {
        try {
            val publicLink = uploadService.upload(file)

            listOf(ThumbnailService.Size(128, 128),
                    ThumbnailService.Size(192, 192),
                    ThumbnailService.Size(256, 256),
                    ThumbnailService.Size(320, 320))
                    .forEach { fireThumbnailCreation(file.originalFilename!!, it) }

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '${file.originalFilename}' to $publicLink !")

        } catch (e: Exception) {
            log.error(e.message, e)
            redirectAttributes.addFlashAttribute("message",
                    "Error: ${e} !")
        }

        return "redirect:/"
    }

    private fun fireThumbnailCreation(originalFilename: String, size: ThumbnailService.Size) {
        launch {
            createThumbnail(originalFilename, size)
        }
    }

    suspend fun createThumbnail(originalFilename: String, size: ThumbnailService.Size) {
        thumbnailService.createThumbnail(originalFilename, ThumbnailService.ScaleType.THUMBNAIL, size)
    }

}
