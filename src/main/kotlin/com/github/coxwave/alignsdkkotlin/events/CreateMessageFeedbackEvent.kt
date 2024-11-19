package com.github.coxwave.alignsdkkotlin.events

import com.github.coxwave.alignsdkkotlin.Constants
import build.buf.gen.ingestion.v1alpha.Event
import build.buf.gen.ingestion.v1alpha.EventProperties
import build.buf.gen.ingestion.v1alpha.EventProperties.FeedbackProperties.Target.TARGET_MESSAGE

class CreateMessageFeedbackEvent(
    private val sessionId: String,
    private val messageIndex: Int,
    private val feedbackType: String
) : BaseEvent() {
    override var eventType = Constants.EVENT_FEEDBACK_CREATE

    init {
        require(sessionId.isNotBlank()) { "sessionId is required" }
        require(sessionId.length <= 64) { "sessionId must be at most 64 characters" }
        require(messageIndex > 0) { "messageIndex must be greater than 0" }
        require(feedbackType in listOf("thumbs_up", "thumbs_down")) { "feedbackType must be 'thumbs_up' or 'thumbs_down'" }
    }

    override fun asProtoEventBuilder(): Event.Builder {
        val propsBuilder = EventProperties.FeedbackProperties.newBuilder()
            .setSessionId(sessionId)
            .setMessageIndexHint(messageIndex)
            .setFeedbackTarget(TARGET_MESSAGE)
            .setType(feedbackType)
        
        return super.asProtoEventBuilder().setProperties(EventProperties.newBuilder().setFeedbackProperties(propsBuilder)).setType(eventType)
    }
}