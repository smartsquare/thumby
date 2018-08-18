package com.sq.image.generator.storage

import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path

@Service
class GCloudStorageAdapter(@Value("\${gcp.bucket-name}") val bucket: String) {

    companion object {
        val storage = StorageOptions.getDefaultInstance().service
    }

    fun downloadImage(fileName: String): Path {
        val tempFiles = Files.createTempDirectory("thumbGenerator").resolve(fileName)

        val blob = storage
                .get(bucket, "${fileName}")

        if (blob == null) {
            throw IllegalArgumentException("Bucket [${bucket}] not found on cloud storage.")
        }

        blob.downloadTo(tempFiles)

        return tempFiles
    }

    fun storeImage(imageName: String, resizedFilePath: Path) {

        val bytes = Files.readAllBytes(resizedFilePath)

        storage.create(BlobInfo
                .newBuilder(bucket, "thumbnails/${imageName}/${resizedFilePath.fileName}")
                .build(),
                bytes)

    }
}