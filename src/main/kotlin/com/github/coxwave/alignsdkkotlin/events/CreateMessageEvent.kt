package com.github.coxwave.alignsdkkotlin.events

import com.github.coxwave.alignsdkkotlin.Constants
import build.buf.gen.ingestion.v1alpha.Event
import build.buf.gen.ingestion.v1alpha.EventProperties

enum class MessageRole {
    USER,
    ASSISTANT,
}

class CreateMessageEvent(
    private val sessionId: String,
    private val messageIndex: UInt,
    private val role: MessageRole,
    private val content: String,
    private val customProperties: Map<String, String> = mapOf(),
) : BaseEvent() {
    override var eventType = Constants.EVENT_CREATE_MESSAGE

    init {
        require(sessionId.isNotBlank()) { "sessionId is required" }
        require(sessionId.length <= 64) { "sessionId must be at most 64 characters" }
        require(messageIndex > 0u) { "messageIndex must be greater than 0" }
        require(content.isNotBlank()) { "content is required" }
        require(customProperties.keys.size <= 10) { "customProperties must have at most 10 keys" }
        customProperties.forEach { (key, value) ->
            require(key.isNotBlank()) { "key of customProperty is required" }
            require(Constants.CUSTOM_PROPERTY_KEY_PATTERN.matches(key)) { "key of customProperty must match ${Constants.CUSTOM_PROPERTY_KEY_PATTERN}" }
            require(value.length <= 256) { "value of customProperty must be at most 256 characters" }
        }
    }

    override fun asProtoEventBuilder(): Event.Builder {
        val messagePropsBuilder = EventProperties.MessageProperties.newBuilder()
            .setSessionId(sessionId)
            .setMessageIndexHint(messageIndex.toInt())
            .setMessageRole(
                when (role) {
                    MessageRole.ASSISTANT -> EventProperties.MessageProperties.Role.ROLE_ASSISTANT
                    MessageRole.USER -> EventProperties.MessageProperties.Role.ROLE_USER
                }
            )
            .setMessageContent(content)
        val propsBuilder = EventProperties.newBuilder()
            .setMessageProperties(messagePropsBuilder)
        customProperties.forEach { (key, value) ->
            propsBuilder.putCustomProperties(
                key,
                EventProperties.CustomPropertyValue.newBuilder().setStringValue(value).build()
            )
        }
        return super.asProtoEventBuilder().setProperties(propsBuilder)
    }
}