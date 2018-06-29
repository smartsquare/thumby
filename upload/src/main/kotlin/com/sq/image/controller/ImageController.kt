package com.sq.image.controller

import com.sq.image.service.ImageService
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
class ImageController(val imageService: ImageService) {

    val log = LogManager.getLogger()

    @GetMapping("/image")
    fun images() = "image"

    @RequestMapping("/image/list")
    @ResponseBody
    fun list(): List<String> {
        return imageService.listAllImagesFromBucket()
    }

    //Mime type in 'produces' is not always correct, but works well enough with most browsers.
    @GetMapping("/image/get/{filename}", produces = arrayOf("image/jpeg"))
    @ResponseBody
    fun get(@PathVariable("filename") filename: String): ByteArray {
        return imageService.retrieveImageByFilename(filename)
    }
}
