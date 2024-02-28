package com.github.coxwave.alignsdkkotlin

import com.connectrpc.ConnectException

class APIException(cause: ConnectException) : Exception("${cause.code}: ${cause.message}")