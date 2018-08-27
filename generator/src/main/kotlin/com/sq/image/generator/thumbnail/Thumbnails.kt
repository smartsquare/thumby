package com.sq.image.generator.thumbnail

import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class Thumbnails {

    fun createThumbnail(image: Path, width: Int, height: Int, resizedFilePath: Path) {
        Thumbnails.of(image.toFile())
                .size(width, height)
                .toFile(resizedFilePath.toFile())
    }

}