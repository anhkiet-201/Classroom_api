package com.chin.ClassroomApi.Utils

import org.springframework.http.ResponseEntity

typealias JsonResponseType = ResponseEntity<Map<String, Any?>>

class ResponseBuilder {

    private var status: Int
    private var message: String
    private var reason: String
    private var data: Any?

    constructor(status: Int, message: String, reason: String, data: Any?) {
        this.status = status
        this.message = message
        this.reason = reason
        this.data = data
    }

    constructor(status: Int, message: String, reason: String) {
        this.status = status
        this.message = message
        this.reason = reason
        this.data = null
    }

    constructor(status: Int, message: String, data: Any?) {
        this.status = status
        this.message = message
        this.data = data
        this.reason = ""
    }

    constructor(status: Int, message: String) {
        this.status = status
        this.message = message
        this.data = null
        this.reason = ""
    }


    companion object {
        const val OK = 200
        const val CREATED = 201
        const val NO_CONTENT = 204
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val NOT_FOUND = 404
    }

    fun toResponse(): JsonResponseType {
        var map = mapOf(
            "status" to status,
            "message" to message,
            "data" to (data ?: "null"),
        )
        if (reason.isNotBlank())
            map = map.plus(
            "reason" to reason
            )
        return ResponseEntity.status(status).body(
            map
        )
    }
}

inline fun<T> T.response(success: (T) -> ResponseBuilder, error: (T) -> ResponseBuilder): JsonResponseType {
    return when (this) {
        null -> error(this)
        else -> success(this)
    }.toResponse()
}

infix fun<T> T.responseCustom(builder: (T) -> ResponseBuilder): JsonResponseType {
    return builder(this).toResponse()
}