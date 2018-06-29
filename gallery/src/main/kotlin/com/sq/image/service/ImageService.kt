package com.sq.image.service

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ImageService(@Value("\${gcp.bucket-name}") val bucket: String,
                   @Value("\${gcp.thumbnail-subfolder}") val subfolder: String) {

    companion object {
        val storage = StorageOptions.getDefaultInstance().service
    }

    fun listAllImagesFromBucket(): List<String> {
        return storage.list(bucket, Storage.BlobListOption.prefix(subfolder)).iterateAll().map { b -> b.selfLink }
    }

    fun retrieveImageByFilename(filename: String): ByteArray {
        return storage[BlobId.of(bucket, filename)].getContent()
    }

}