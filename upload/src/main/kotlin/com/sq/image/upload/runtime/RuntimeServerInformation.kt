package com.sq.image.upload.runtime

import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.net.InetAddress


@Service
class RuntimeServerInformation(val environment: Environment) {


    fun getServerInfo(): ServerInfo {
        // Port
        val port = environment.getProperty("local.server.port") ?: "-1"


        // Local address
        val localHostAddress = InetAddress.getLocalHost().hostAddress
        val localHostName = InetAddress.getLocalHost().hostName

        // Remote address
        val loopbackHostAddress = InetAddress.getLoopbackAddress().hostAddress
        val loopbackHostName = InetAddress.getLoopbackAddress().hostName


        return ServerInfo(port, localHostAddress, localHostName, loopbackHostAddress, loopbackHostName)
    }

}

@Service
class MyListener : ApplicationListener<ServletWebServerInitializedEvent> {

    var port = 0

    override fun onApplicationEvent(event: ServletWebServerInitializedEvent) {
        port = (event.webServer.port)
    }


}