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
) : BaseEvent() {
    override var eventType = Constants.EVENT_CREATE_MESSAGE

    override fun asProtoEventBuilder(): Event.Builder {
        val propsBuilder = EventProperties.MessageProperties.newBuilder()
            .setSessionId(sessionId)
            .setMessageIndexHint(messageIndex.toInt())
            .setMessageRole(
                when (role) {
                    MessageRole.ASSISTANT -> EventProperties.MessageProperties.Role.ROLE_ASSISTANT
                    MessageRole.USER -> EventProperties.MessageProperties.Role.ROLE_USER
                }
            )
            .setMessageContent(content)
        return super.asProtoEventBuilder().setProperties(EventProperties.newBuilder().setMessageProperties(propsBuilder))
    }
}