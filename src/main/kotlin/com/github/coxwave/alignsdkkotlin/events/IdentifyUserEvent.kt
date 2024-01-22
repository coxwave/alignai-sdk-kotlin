package com.github.coxwave.alignsdkkotlin.events

import com.github.coxwave.alignsdkkotlin.Constants
import build.buf.gen.ingestion.v1alpha.Event
import build.buf.gen.ingestion.v1alpha.EventProperties
import com.google.protobuf.util.Timestamps
import java.time.Instant

class IdentifyUserEvent(
    private val userId: String,
    private val userDisplayName: String? = null,
    private val userEmail: String? = null,
    private val userIp: String? = null,
    private val userCountryCode: String? = null,
    private val userCreateTime: Instant? = null,
): BaseEvent() {
    override var eventType = Constants.EVENT_IDENTIFY_USER

    override fun asProtoEventBuilder(): Event.Builder {
        val propsBuilder = EventProperties.UserProperties.newBuilder().setUserId(userId)
        if (userDisplayName != null) {
            propsBuilder.userDisplayName = userDisplayName
        }
        if (userEmail != null) {
            propsBuilder.userEmail = userEmail
        }
        if (userCountryCode != null) {
            propsBuilder.userLocation = EventProperties.UserProperties.Location.newBuilder().setCountryCode(userCountryCode).build()
        }
        if (userCountryCode == null && userIp != null) {
            propsBuilder.userIp = userIp
        }
        if (userCreateTime != null) {
            propsBuilder.userCreateTime = Timestamps.fromMillis(userCreateTime.toEpochMilli())
        }
        return super.asProtoEventBuilder().setProperties(EventProperties.newBuilder().setUserProperties(propsBuilder))
    }
}