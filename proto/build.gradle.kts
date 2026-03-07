plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.protobuf)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    api(libs.grpc.kotlin.stub)
    api(libs.grpc.protobuf)
    api(libs.protobuf.kotlin)  // Java protobuf runtime
    api(libs.coroutines.core)  // Required by generated gRPC-Kotlin server-streaming stubs
    // Align grpc-stub version with protoc-gen-grpc-java to avoid API mismatch
    api("io.grpc:grpc-stub:${libs.versions.grpc.java.get()}")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.java.get()}"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${libs.versions.grpc.kotlin.get()}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
            // Note: Kotlin DSL wrappers (builtins { create("kotlin") }) omitted
            // to avoid circular dependency between compileJava and compileKotlin.
            // Use Java protobuf classes directly with .newBuilder() pattern.
        }
    }
}
