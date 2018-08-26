package com.sq.image.upload.uploader

import com.sq.image.upload.thumbnail.Size
import com.sq.image.upload.thumbnail.ThumbnailService
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

            thumbnailService.createThumbnails(file.originalFilename!!,
                    Size(128, 128),
                    Size(192, 192),
                    Size(256, 256),
                    Size(320, 320))

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '${file.originalFilename}' to $publicLink !")

        } catch (e: Exception) {
            log.error(e.message, e)
            redirectAttributes.addFlashAttribute("message",
                    "Error: ${e} !")
        }

        return "redirect:/"
    }

}
