package com.sq.image.gallery

import com.sq.image.shared.storage.GCloudStorageAdapter
import com.sq.image.shared.storage.ImageInfo
import org.springframework.stereotype.Service

@Service
class GalleryService(private val cloudAdapter: GCloudStorageAdapter) {

    fun listAllImagesFromBucket(): Map<String, List<ImageInfo>> = cloudAdapter.listContentInBucket()

    fun retrieveImageByFilename(filename: String): String = cloudAdapter.retrieveImageBytesByFilename(filename)
}
