package com.github.coxwave.alignsdkkotlin

object Constants {
    const val DEFAULT_API_HOST = "https://api.impaction.ai"

    const val ASSISTANT_DEFAULT_ID = "DEFAULT"
    const val EVENT_IDENTIFY_USER = "user_recognize"
    const val EVENT_OPEN_SESSION = "session_open"
    const val EVENT_CLOSE_SESSION = "session_close"
    const val EVENT_CREATE_MESSAGE = "message_create"

    val CUSTOM_PROPERTY_KEY_PATTERN = """^[a-zA-Z][a-zA-Z0-9_]{0,63}$""".toRegex()
}