package com.sq.image.service

import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class UploadService(@Value("\${gcp.bucket-name}") val bucket: String) {

    companion object {
        val storage = StorageOptions.getDefaultInstance().getService()
    }

    fun upload(file: MultipartFile): String {

        val fileName = file.originalFilename

        if (file.isEmpty) {
            throw UploadException("Failed to upload empty file $fileName")
        }

        val blobInfo = storage.create(
                BlobInfo
                        .newBuilder(bucket, fileName)
                        .build(),
                        file.bytes)

        // return the public download link
        return blobInfo.getMediaLink()
    }

}