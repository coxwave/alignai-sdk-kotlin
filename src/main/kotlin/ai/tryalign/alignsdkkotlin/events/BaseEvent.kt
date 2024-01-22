package ai.tryalign.alignsdkkotlin.events

import build.buf.gen.ingestion.v1alpha.Event
import com.google.protobuf.util.Timestamps
import java.time.Instant
import java.util.*

open class BaseEvent {
    private val eventId = UUID.randomUUID().toString().replace("-", "")
    private val eventCreateTime: Instant = Instant.now()
    open lateinit var eventType: String

    open fun asProtoEventBuilder(): Event.Builder {
        return Event.newBuilder().setId(eventId).setCreateTime(Timestamps.fromMillis(eventCreateTime.toEpochMilli())).setType(eventType)
    }
}