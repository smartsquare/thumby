package com.sq.image.generator.thumbnail

import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class ThumbPathCreator {

    fun createNewImagePath(image: Path, imageName: String, width: Int, height: Int): Path {
        val newImageName = getResultFileName(imageName, width, height)
        return image.parent.resolve(newImageName)
    }

    private fun getResultFileName(imageName: String, width: Int, height: Int): String {
        val splitName = imageName.split(".")
        return "${splitName[0]}-${width}x${height}.${splitName[1]}"
    }

}