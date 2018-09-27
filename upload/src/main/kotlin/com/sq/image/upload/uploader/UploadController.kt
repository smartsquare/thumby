package com.sq.image.upload.uploader

import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@Controller
class UploadController(
        val uploadService: UploadService
) {

    private val log = LogManager.getLogger()

    @GetMapping("/")
    fun index() = "upload"

    @PostMapping("/upload")
    @ResponseBody
    fun handleFileUpload(@RequestParam("file") file: MultipartFile): String {
        val filepath = uploadService.upload(file)

        log.info("Image ${file.originalFilename} successfully uploaded to ${filepath}")

        return file.originalFilename!!
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleError(req: HttpServletRequest, ex: Exception): String {
        log.error("Request: " + req.requestURL + " raised " + ex)
        return "upload fail"
    }

}
