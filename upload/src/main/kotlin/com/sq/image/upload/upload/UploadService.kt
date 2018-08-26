package com.sq.image.service

import com.sq.image.shared.storage.GCloudStorageAdapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class UploadService(@Value("\${gcp.bucket-name}") val bucket: String,
                    val storageAdapter: GCloudStorageAdapter) {

    fun upload(file: MultipartFile): String {
        val fileName = file.originalFilename ?: throw IllegalArgumentException("File name is not expected to be empty.")
        return storageAdapter.upload(bucket, fileName, file.bytes)
    }

}