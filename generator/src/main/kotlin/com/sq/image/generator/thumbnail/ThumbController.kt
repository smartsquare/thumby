package com.sq.image.generator.thumbnail

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ThumbController(private val generator: ThumbGenerator) {

    @PostMapping("img/{imageName}/thumb/{width}/{height}")
    fun thumb(@PathVariable("imageName") imageName:String,
              @PathVariable("width") width:Int,
              @PathVariable("height") height:Int
              ){

        generator.createThumb(imageName, width, height)
    }
}