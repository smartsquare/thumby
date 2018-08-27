package com.sq.image.upload.uploader

import com.sq.image.shared.storage.GCloudStorageAdapter
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class UploadService(private val storageAdapter: GCloudStorageAdapter) {

    fun upload(file: MultipartFile): String {
        val fileName = file.originalFilename!!
        if (fileName.isNullOrEmpty()) {
            throw IllegalArgumentException("File name is not expected to be empty.")
        }

        return storageAdapter.upload(fileName, file.bytes)
    }

}