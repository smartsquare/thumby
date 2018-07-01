package com.sq.image.controller

import com.sq.image.service.ImageService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ImageController(val imageService: ImageService) {

    @GetMapping("/image/list")
    fun list(): List<String> {
        return imageService.listAllImagesFromBucket()
    }

    @GetMapping("/image", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun loadImage(name: String): String {
        return imageService.retrieveImageByFilename(name)
    }

}