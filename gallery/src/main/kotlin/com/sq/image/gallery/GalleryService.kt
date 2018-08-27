package com.sq.image.gallery

import com.sq.image.shared.storage.GCloudStorageAdapter
import com.sq.image.shared.storage.ImageInfo
import org.springframework.stereotype.Service

@Service
class GalleryService(private val cloudAdapter: GCloudStorageAdapter) {

    fun listAllImagesFromBucket(): Map<String, List<ImageInfo>> {
        return cloudAdapter.listContentInBucket()
    }

    fun retrieveImageByFilename(filename: String): String {
        return cloudAdapter.retrieveImageBytesByFilename(filename)
    }

}