plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
}

group = "com.github.coxwave"
version = "0.1.1"

repositories {
    mavenCentral()
    maven {
        name = "buf"
        url = uri("https://buf.build/gen/maven")
    }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.connectrpc:connect-kotlin-okhttp:0.4.0")
    implementation("com.connectrpc:connect-kotlin-google-java-ext:0.4.0")
    implementation("com.google.protobuf:protobuf-java:3.25.2")
    implementation("build.buf.gen:impaction-ai_ingestion_connectrpc_kotlin:0.4.0.1.20230724010212.6e96e3f59731")
    implementation("build.buf.gen:impaction-ai_ingestion_protocolbuffers_kotlin:25.2.0.1.20230724010212.6e96e3f59731")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.github.coxwave"
            artifactId = "alignai-sdk-kotlin"
        }
    }
}
