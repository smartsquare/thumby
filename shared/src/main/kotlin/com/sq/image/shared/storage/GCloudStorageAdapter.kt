package com.sq.image.shared.storage

import com.google.cloud.storage.Blob
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import java.nio.file.Files
import java.nio.file.Path

@Service
class GCloudStorageAdapter(
        @Value("\${gcp.bucket-name}") private val bucket: String,
        @Value("\${gcp.thumbnail-subfolder:thumbnails}") private val subfolder: String
) {

    companion object {
        val storage = StorageOptions.getDefaultInstance().service
    }

    fun downloadImage(fileName: String): Path {
        val tempFiles = Files.createTempDirectory("thumbGenerator").resolve(fileName)

        val blob = storage.get(bucket, fileName)
                ?: throw IllegalArgumentException("Bucket [$bucket] not found on cloud storage.")

        blob.downloadTo(tempFiles)

        return tempFiles
    }

    fun upload(fileName: String, bytes: ByteArray): String {

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

    fun storeImage(imageName: String, resizedFilePath: Path) {

        val bytes = Files.readAllBytes(resizedFilePath)

        storage.create(BlobInfo
                .newBuilder(bucket, "$subfolder/$imageName/${resizedFilePath.fileName}")
                .build(),
                bytes)
    }

    fun listContentInBucket(): Map<String, List<ImageInfo>> {
        val bucketContent = storage.list(bucket, Storage.BlobListOption.prefix(subfolder))

        return bucketContent.iterateAll()
                .map(this@GCloudStorageAdapter::toImageInfo)
                .groupBy { it.name }
    }

    fun retrieveImageBytesByFilename(filename: String): String {
        val content = storage[BlobId.of(bucket, filename)].getContent()
        return Base64Utils.encodeToString(content)
    }

    private fun toImageInfo(imageBLob: Blob): ImageInfo {
        val path = imageBLob.name
        val fileName = path.split("/")[1]

        return ImageInfo(fileName, path)
    }
}
