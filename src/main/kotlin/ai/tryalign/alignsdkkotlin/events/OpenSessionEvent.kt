package ai.tryalign.alignsdkkotlin.events

import ai.tryalign.alignsdkkotlin.Constants
import build.buf.gen.ingestion.v1alpha.Event
import build.buf.gen.ingestion.v1alpha.EventProperties

class OpenSessionEvent(
    private val sessionId: String,
    private val userId: String,
    private val assistantId: String = Constants.ASSISTANT_DEFAULT_ID,
) : BaseEvent() {
    override var eventType = Constants.EVENT_OPEN_SESSION

    override fun asProtoEventBuilder(): Event.Builder {
        val propsBuilder = EventProperties.SessionProperties.newBuilder()
            .setSessionId(sessionId)
            .setUserId(userId)
            .setAssistantId(assistantId)
        return super.asProtoEventBuilder().setProperties(EventProperties.newBuilder().setSessionProperties(propsBuilder))
    }
}