package com.sq.image.upload.uploader

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile

@Controller
class UploadController(
        val uploadService: UploadService
) {

    private val log = LogManager.getLogger()

    @GetMapping("/")
    fun index() = "upload"

    @PostMapping("/upload")
    @ResponseBody
    fun handleFileUpload(
            @RequestParam("file") file: MultipartFile
    ): String =
            try {
                uploadService.upload(file)
                file.originalFilename!!
            } catch (e: Exception) {
                log.error(e.message, e)
                "error: ${e.message}"
            }

}
