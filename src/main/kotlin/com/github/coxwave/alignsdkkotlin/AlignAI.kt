package com.github.coxwave.alignsdkkotlin

import com.github.coxwave.alignsdkkotlin.events.BaseEvent
import build.buf.gen.ingestion.v1alpha.CollectEventsRequest
import build.buf.gen.ingestion.v1alpha.IngestionServiceClient
import com.connectrpc.ProtocolClientConfig
import com.connectrpc.extensions.GoogleJavaProtobufStrategy
import com.connectrpc.impl.ProtocolClient
import com.connectrpc.okhttp.ConnectOkHttpClient
import com.connectrpc.protocols.NetworkProtocol
import okhttp3.OkHttpClient
import java.util.*

class AlignAI(
    private val config: Configuration,
) {
    private val ingestionClient: IngestionServiceClient

    init {
        require(config.isValid()) { "Invalid configuration" }
        val client = ProtocolClient(
            httpClient = ConnectOkHttpClient(OkHttpClient()),
            ProtocolClientConfig(
                host = config.apiHost,
                serializationStrategy = GoogleJavaProtobufStrategy(),
                networkProtocol = NetworkProtocol.CONNECT,
            ),
        )
        ingestionClient = IngestionServiceClient(client)
    }

    suspend fun collectEvents(events: List<BaseEvent>) {
        val builder = CollectEventsRequest.newBuilder().setRequestId(UUID.randomUUID().toString().replace("-", ""))
        events.forEach { event -> builder.addEvents(event.asProtoEventBuilder().setProjectId(config.projectId)) }
        ingestionClient.collectEvents(
            builder.build(),
            mapOf(
                "Authorization" to listOf("Bearer ${config.apiKey}")
            ),
        )
    }
}