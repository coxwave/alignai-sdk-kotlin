# Align AI Kotlin SDK

[![Jitpack release](https://jitpack.io/v/coxwave/alignai-sdk-kotlin.svg)](https://jitpack.io/#coxwave/alignai-sdk-kotlin)
![License](https://img.shields.io/github/license/coxwave/alignai-sdk-kotlin)

This is the official server-side JVM SDK for Align AI. This library allows you to easily send your data to Align AI.

Visit the [full documentation](https://docs.tryalign.ai/kotlin-sdk.html) to see the detailed usage.

# Installation

Install the Kotlin SDK through Build chain (Gradle, Maven):

```gradle
repositories {
    mavenCentral()

    // Jitpack for Align AI SDK itself
    maven {
        url = uri("https://jitpack.io")
    }

    // Buf for Align AI API Schema (Dependencies)
    maven {
        name = "buf"
        url = uri("https://buf.build/gen/maven")
    }
}

dependencies {
    ...

    // SDK
    implementation("com.github.coxwave:alignai-sdk-kotlin:0.1.2")

    // SDK Dependencies
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.connectrpc:connect-kotlin-okhttp:0.4.0")
    implementation("com.connectrpc:connect-kotlin-google-java-ext:0.4.0")
    implementation("com.google.protobuf:protobuf-java:3.25.2")
    implementation("build.buf.gen:impaction-ai_ingestion_connectrpc_kotlin:0.4.0.1.20230724010212.6e96e3f59731")
    implementation("build.buf.gen:impaction-ai_ingestion_protocolbuffers_kotlin:25.2.0.1.20230724010212.6e96e3f59731")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
```

# Contribution

If you have any issues with the SDK, feel free to open Github issues and we'll get to it as soon as possible.

We welcome your contributions and suggestions!