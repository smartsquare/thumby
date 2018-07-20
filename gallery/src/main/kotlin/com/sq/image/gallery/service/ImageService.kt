package com.sq.image.service

import com.google.cloud.storage.Blob
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import com.sq.image.gallery.service.ImageInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils

@Service
class ImageService(@Value("\${gcp.bucket-name}") val bucket: String,
                   @Value("\${gcp.thumbnail-subfolder}") val subfolder: String) {

    companion object {
        val storage = StorageOptions.getDefaultInstance().service
    }

    fun listAllImagesFromBucket(): Map<String, List<ImageInfo>> {
        val bucketContent = storage.list(bucket, Storage.BlobListOption.prefix(subfolder))

        return bucketContent.iterateAll()
                .map(this@ImageService::toImageInfo)
                .groupBy { it.name }
    }

    fun toImageInfo(imageBLob: Blob): ImageInfo {
        val path = imageBLob.name
        val fileName = path.split("/")[1]

        return ImageInfo(fileName, path)
    }

    fun retrieveImageByFilename(filename: String): String {
        val content = storage[BlobId.of(bucket, filename)].getContent()
        return Base64Utils.encodeToString(content)
    }

}