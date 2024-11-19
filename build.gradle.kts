plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
}

group = "com.github.coxwave"
version = "0.1.2"

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
    implementation("com.connectrpc:connect-kotlin-okhttp:0.7.1")
    implementation("com.connectrpc:connect-kotlin-google-java-ext:0.7.1")
    implementation("com.google.protobuf:protobuf-kotlin:4.28.3")
    implementation("build.buf.gen:impaction-ai_ingestion_connectrpc_kotlin:0.7.1.1.20240908234409.342910308823")
    implementation("build.buf.gen:impaction-ai_ingestion_protocolbuffers_kotlin:28.3.0.2.20240908234409.342910308823")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
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
