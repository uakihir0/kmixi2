import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.wire)
}

wire {
    sourcePath {
        srcDir("src/main/proto")
    }
    prune(
        "social.mixi.application.service.application_api.v1.ApplicationService",
        "social.mixi.application.service.application_stream.v1.ApplicationService",
    )
    kotlin {
    }
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    js(IR) {
        nodejs()
        browser()
        binaries.library()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    linuxX64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            api(libs.kgrpc.core)
            api(libs.wire.runtime)
            api(libs.coroutines.core)
        }
    }
}
