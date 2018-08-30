package com.sq.image.generator.thumbnail

import com.sq.image.shared.storage.GCloudStorageAdapter
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils

@Service
class ThumbGenerator(
        private val storageAdapter: GCloudStorageAdapter,
        private val thumbnails: Thumbnails,
        private val pathCreator: ThumbPathCreator
) {

    fun createThumb(imageName: String, width: Int, height: Int) {

        val image = storageAdapter.downloadImage(imageName)

        val resizedFilePath = pathCreator.createNewImagePath(image, imageName, width, height)

        thumbnails.createThumbnail(image, width, height, resizedFilePath)

        storageAdapter.storeImage(imageName, resizedFilePath)

        FileSystemUtils.deleteRecursively(image.parent)
    }
}
