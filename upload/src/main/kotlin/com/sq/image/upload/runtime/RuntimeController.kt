package com.sq.image.upload.runtime

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RuntimeController(val runtimeInfo: RuntimeServerInformation) {

    @GetMapping("/server")
    fun serverInfo(): ServerInfo {
        return runtimeInfo.getServerInfo()
    }
}