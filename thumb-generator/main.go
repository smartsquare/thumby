package main;

import (
    _ "image/jpeg"
    _ "image/png"
    "os"
    "log"
)

func main() {
    var storage StorageService
    if os.Getenv("STORAGE_MODE") == "local-fs" {
        log.Println("Using local filesystem (at ./data) for storage")
        storage = NewLocalFsStorageService()
    } else {
        log.Println("Using google cloud storage for storage")
        storage = NewGoogleCloudStorageService()
    }

    e := NewEngine(storage)

    e.Run(":8888")
}
