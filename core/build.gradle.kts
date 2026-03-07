import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    // FIXME: Re-enable protobuf plugin when implementing actual gRPC calls.
    // The protobuf Gradle plugin requires the Java plugin applied first,
    // which conflicts with kotlin-multiplatform. Proto files remain in
    // core/src/main/proto/ for reference.
    // alias(libs.plugins.protobuf)
    id("module.publications")
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    js(IR) {
        nodejs()
        browser()
        binaries.library()
        generateTypeScriptDefinitions()

        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xenable-suspend-function-exporting")
                }
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }

        commonMain.dependencies {
            implementation(libs.ktor.core)
            implementation(libs.kmpcommon)
            implementation(libs.khttpclient)
            implementation(libs.datetime)
            implementation(libs.serialization.json)
        }

        jvmMain.dependencies {
            // gRPC (JVM only)
            // FIXME: Replace with KMP gRPC library when available
            implementation(libs.grpc.kotlin.stub)
            implementation(libs.grpc.protobuf)
            implementation(libs.grpc.netty.shaded)
            implementation(libs.protobuf.kotlin)
        }

        // for test (kotlin/jvm)
        jvmTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotest.junit5)
            implementation(libs.kotest.assertions)
            implementation(libs.coroutines.test)
        }
    }
}

// FIXME: Re-enable protobuf code generation when implementing gRPC calls.
// protobuf {
//     protoc {
//         artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
//     }
//     plugins {
//         create("grpc") {
//             artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.java.get()}"
//         }
//         create("grpckt") {
//             artifact = "io.grpc:protoc-gen-grpc-kotlin:${libs.versions.grpc.kotlin.get()}:jdk8@jar"
//         }
//     }
//     generateProtoTasks {
//         all().forEach {
//             it.plugins {
//                 create("grpc")
//                 create("grpckt")
//             }
//             it.builtins {
//                 create("kotlin")
//             }
//         }
//     }
// }

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}
