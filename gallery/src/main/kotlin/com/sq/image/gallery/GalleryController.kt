package com.sq.image.gallery

import com.sq.image.shared.storage.ImageInfo
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class GalleryController(private val galleryService: GalleryService) {

    @GetMapping("/")
    fun index() = "index"

    @GetMapping("/image/list")
    @ResponseBody
    fun list(): Map<String, List<ImageInfo>> {
        return galleryService.listAllImagesFromBucket()
    }

    @GetMapping("/image", produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseBody
    fun loadImage(name: String): String {
        return galleryService.retrieveImageByFilename(name)
    }

}
