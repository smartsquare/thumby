package com.sq.image.generator.thumbnail

import com.sq.image.shared.storage.GCloudStorageAdapter
import net.coobird.thumbnailator.Thumbnails
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils

@Service
class ThumbGenerator(@Value("\${gcp.bucket-name}") val bucket: String,
                     val storageAdapter: GCloudStorageAdapter) {

    fun createThumb(imageName: String, width: Int, height: Int) {

        val image = storageAdapter.downloadImage(bucket, imageName)

        val newImageName = getResultFileName(imageName, width, height)

        val resizedFilePath = image.parent.resolve(newImageName)
        Thumbnails.of(image.toFile())
                .size(width, height)
                .toFile(resizedFilePath.toFile())

        storageAdapter.storeImage(bucket, imageName, resizedFilePath)

        FileSystemUtils.deleteRecursively(image.parent)
    }

    private fun getResultFileName(imageName: String, width: Int, height: Int): String {
        val splitName = imageName.split(".")
        val newImageName = "${splitName[0]}-${width}x${height}.${splitName[1]}"
        return newImageName
    }

}