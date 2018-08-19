package com.sq.image.upload.runtime

data class ServerInfo(val port: String,
                      val localHostAddress: String,
                      val localHostName: String,
                      val loopbackHostAddress: String,
                      val loopbackHostName: String)