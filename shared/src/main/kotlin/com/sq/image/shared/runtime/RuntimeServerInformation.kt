package com.sq.image.shared.runtime

import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service
import java.net.InetAddress

@Service
class RuntimeServerInformation : ApplicationListener<ServletWebServerInitializedEvent> {

    var port = -1

    fun getServerInfo(): ServerInfo {
        val localHostAddress = InetAddress.getLocalHost().hostAddress
        val localHostName = InetAddress.getLocalHost().hostName
        val loopbackHostAddress = InetAddress.getLoopbackAddress().hostAddress
        val loopbackHostName = InetAddress.getLoopbackAddress().hostName

        return ServerInfo(port, localHostAddress, localHostName, loopbackHostAddress, loopbackHostName)
    }

    override fun onApplicationEvent(event: ServletWebServerInitializedEvent) {
        port = event.webServer.port
    }
}
