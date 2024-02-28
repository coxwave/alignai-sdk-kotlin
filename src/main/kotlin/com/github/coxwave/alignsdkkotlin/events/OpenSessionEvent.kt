package com.github.coxwave.alignsdkkotlin.events

import com.github.coxwave.alignsdkkotlin.Constants
import build.buf.gen.ingestion.v1alpha.Event
import build.buf.gen.ingestion.v1alpha.EventProperties

class OpenSessionEvent(
    private val sessionId: String,
    private val userId: String,
    private val assistantId: String = Constants.ASSISTANT_DEFAULT_ID,
) : BaseEvent() {
    override var eventType = Constants.EVENT_OPEN_SESSION

    init {
        require(sessionId.isNotBlank()) { "sessionId is required" }
        require(sessionId.length <= 64) { "sessionId must be at most 64 characters" }
        require(userId.isNotBlank()) { "userId is required" }
        require(userId.length <= 64) { "userId must be at most 64 characters" }
        require(assistantId.isNotBlank()) { "assistantId is required" }
        require(assistantId.length <= 64) { "assistantId must be at most 64 characters" }
    }

    override fun asProtoEventBuilder(): Event.Builder {
        val propsBuilder = EventProperties.SessionProperties.newBuilder()
            .setSessionId(sessionId)
            .setUserId(userId)
            .setAssistantId(assistantId)
        return super.asProtoEventBuilder().setProperties(EventProperties.newBuilder().setSessionProperties(propsBuilder))
    }
}