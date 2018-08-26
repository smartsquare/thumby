package com.sq.image.shared.storage

import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import com.sq.image.shared.UploadException
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path

@Service
class GCloudStorageAdapter {

    companion object {
        val storage = StorageOptions.getDefaultInstance().service
    }

    fun downloadImage(bucket: String, fileName: String): Path {
        val tempFiles = Files.createTempDirectory("thumbGenerator").resolve(fileName)

        val blob = storage.get(bucket, fileName)
                ?: throw IllegalArgumentException("Bucket [${bucket}] not found on cloud storage.")

        blob.downloadTo(tempFiles)

        return tempFiles
    }

    fun upload(bucket: String, fileName: String, bytes: ByteArray): String {

        if (fileName.isEmpty() || bytes.isEmpty()) {
            throw UploadException("Failed to upload empty file $fileName")
        }

        val blobInfo = storage.create(
                BlobInfo
                        .newBuilder(bucket, fileName)
                        .build(),
                bytes)

        // return the public download link
        return blobInfo.mediaLink
    }

    fun storeImage(bucket: String, imageName: String, resizedFilePath: Path) {

        val bytes = Files.readAllBytes(resizedFilePath)

        storage.create(BlobInfo
                .newBuilder(bucket, "thumbnails/${imageName}/${resizedFilePath.fileName}")
                .build(),
                bytes)

    }
}