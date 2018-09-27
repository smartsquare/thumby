package com.sq.image.upload.thumbnail

import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.Random
import javax.servlet.http.HttpServletRequest


@RestController
class ThumbnailController(val thumbnailService: ThumbnailService) {

    private val log = LogManager.getLogger()

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/thumbnail")
    fun createThumbnail(@RequestBody param: ThumbnailData): String {

        Thread.sleep(getRandomInt().toLong())

        if (shouldThrowRandomException()) {
            throw RuntimeException("Mysterious Exception")
        }

        thumbnailService.createThumbnails(param.fileName, Size(param.size, param.size))

        return "success"
    }

    private fun shouldThrowRandomException() = getRandomInt() % 5L == 0L

    private fun getRandomInt() = Random().nextInt(1000)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleError(req: HttpServletRequest, ex: Exception): String {
        log.error("Request: " + req.requestURL + " raised " + ex)
        return "fail"
    }

}

data class ThumbnailData(val fileName: String, val size: Int)
