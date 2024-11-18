plugins {
    kotlin("jvm") version "1.7.0"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.7.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.connectrpc:connect-kotlin-okhttp:0.4.0")
    implementation("com.connectrpc:connect-kotlin-google-java-ext:0.4.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.21.12")
    implementation("build.buf.gen:impaction-ai_ingestion_connectrpc_kotlin:0.7.1.1.20240908234409.342910308823")
    implementation("build.buf.gen:impaction-ai_ingestion_protocolbuffers_kotlin:28.3.0.2.20240908234409.342910308823")
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.7.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-common:1.7.0")
        force("com.squareup.okio:okio:2.10.0")
        force("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        force("com.connectrpc:connect-kotlin:0.4.0")
        force("com.google.protobuf:protobuf-kotlin:3.21.12")
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
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
