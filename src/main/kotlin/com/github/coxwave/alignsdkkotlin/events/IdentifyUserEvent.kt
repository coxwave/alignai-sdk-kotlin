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
    private val customProperties: Map<String, String> = mapOf(),
) : BaseEvent() {
    override var eventType = Constants.EVENT_IDENTIFY_USER

    init {
        require(userId.isNotBlank()) { "userId is required" }
        require(userId.length <= 64) { "userId must be at most 64 characters" }
        require(userDisplayName == null || userDisplayName.length <= 64) { "userDisplayName must be at most 64 characters" }
        require(userEmail == null || userEmail.length <= 256) { "userEmail must be at most 256 characters" }
        require(userCountryCode == null || userCountryCode.length == 2) { "userCountryCode must be ISO Alpha-2 code." }
        require(customProperties.keys.size <= 10) { "customProperties must have at most 10 keys" }
        customProperties.forEach { (key, value) ->
            require(key.isNotBlank()) { "key of customProperty is required" }
            require(Constants.CUSTOM_PROPERTY_KEY_PATTERN.matches(key)) { "key of customProperty must match ${Constants.CUSTOM_PROPERTY_KEY_PATTERN}" }
            require(value.length <= 256) { "value of customProperty must be at most 256 characters" }
        }
    }

    override fun asProtoEventBuilder(): Event.Builder {
        val userPropsBuilder = EventProperties.UserProperties.newBuilder().setUserId(userId)
        if (userDisplayName != null) {
            userPropsBuilder.userDisplayName = userDisplayName
        }
        if (userEmail != null) {
            userPropsBuilder.userEmail = userEmail
        }
        if (userCountryCode != null) {
            userPropsBuilder.userLocation =
                EventProperties.UserProperties.Location.newBuilder().setCountryCode(userCountryCode).build()
        }
        if (userCountryCode == null && userIp != null) {
            userPropsBuilder.userIp = userIp
        }
        if (userCreateTime != null) {
            userPropsBuilder.userCreateTime = Timestamps.fromMillis(userCreateTime.toEpochMilli())
        }
        val propsBuilder = EventProperties.newBuilder()
            .setUserProperties(userPropsBuilder)
        customProperties.forEach { (key, value) ->
            propsBuilder.putCustomProperties(
                key,
                EventProperties.CustomPropertyValue.newBuilder().setStringValue(value).build()
            )
        }
        return super.asProtoEventBuilder().setProperties(propsBuilder)
    }
}