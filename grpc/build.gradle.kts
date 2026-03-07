import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kmpgrpc)
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

    sourceSets {
        commonMain.dependencies {
            api(libs.kmpgrpc.core)
            api(libs.coroutines.core)
        }
    }
}

kmpGrpc {
    common()
    jvm()
    js()
    native()
    includeWellKnownTypes = true
    protoSourceFolders.from(project.files("src/main/proto"))
}
