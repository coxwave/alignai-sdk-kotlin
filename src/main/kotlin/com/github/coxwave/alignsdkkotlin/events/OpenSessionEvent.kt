package com.github.coxwave.alignsdkkotlin.events

import com.github.coxwave.alignsdkkotlin.Constants
import build.buf.gen.ingestion.v1alpha.Event
import build.buf.gen.ingestion.v1alpha.EventProperties

class OpenSessionEvent(
    private val sessionId: String,
    private val userId: String,
    private val assistantId: String = Constants.ASSISTANT_DEFAULT_ID,
    private val customProperties: Map<String, String> = mapOf(),
) : BaseEvent() {
    override var eventType = Constants.EVENT_OPEN_SESSION

    init {
        require(sessionId.isNotBlank()) { "sessionId is required" }
        require(sessionId.length <= 64) { "sessionId must be at most 64 characters" }
        require(userId.isNotBlank()) { "userId is required" }
        require(userId.length <= 64) { "userId must be at most 64 characters" }
        require(assistantId.isNotBlank()) { "assistantId is required" }
        require(assistantId.length <= 64) { "assistantId must be at most 64 characters" }
        require(customProperties.keys.size <= 10) { "customProperties must have at most 10 keys" }
        customProperties.forEach { (key, value) ->
            require(key.isNotBlank()) { "key of customProperty is required" }
            require(Constants.CUSTOM_PROPERTY_KEY_PATTERN.matches(key)) { "key of customProperty must match ${Constants.CUSTOM_PROPERTY_KEY_PATTERN}" }
            require(value.length <= 256) { "value of customProperty must be at most 256 characters" }
        }
    }

    override fun asProtoEventBuilder(): Event.Builder {
        val sessionPropsBuilder = EventProperties.SessionProperties.newBuilder()
            .setSessionId(sessionId)
            .setUserId(userId)
            .setAssistantId(assistantId)
        val propBuilder = EventProperties.newBuilder()
            .setSessionProperties(sessionPropsBuilder)
        customProperties.forEach { (key, value) ->
            propBuilder.putCustomProperties(
                key,
                EventProperties.CustomPropertyValue.newBuilder().setStringValue(value).build()
            )
        }
        return super.asProtoEventBuilder().setProperties(propBuilder)
    }
}