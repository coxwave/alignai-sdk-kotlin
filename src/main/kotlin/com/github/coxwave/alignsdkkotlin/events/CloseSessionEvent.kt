package com.github.coxwave.alignsdkkotlin.events

import com.github.coxwave.alignsdkkotlin.Constants
import build.buf.gen.ingestion.v1alpha.Event
import build.buf.gen.ingestion.v1alpha.EventProperties

class CloseSessionEvent(
    private val sessionId: String,
) : BaseEvent() {
    override var eventType = Constants.EVENT_CLOSE_SESSION

    init {
        require(sessionId.isNotBlank()) { "sessionId is required" }
        require(sessionId.length <= 64) { "sessionId must be at most 64 characters" }
    }

    override fun asProtoEventBuilder(): Event.Builder {
        val propsBuilder = EventProperties.SessionProperties.newBuilder()
            .setSessionId(sessionId)
        return super.asProtoEventBuilder().setProperties(EventProperties.newBuilder().setSessionProperties(propsBuilder))
    }
}