import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.swiftpackage)
    id("module.publications")
}

kotlin {
    js(IR) {
        nodejs()
        browser()
        binaries.library()
        generateTypeScriptDefinitions()

        compilations.all {
            compileTaskProvider.configure {
                compilerOptions.moduleName.set("kmixi2-js")
            }
        }
    }

    val xcf = XCFramework("kmixi2")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64(),
    ).forEach {
        it.binaries.framework {
            export(project(":core"))
            export(project(":stream"))
            baseName = "kmixi2"
            xcf.add(this)
        }
    }

    cocoapods {
        name = "kmixi2"
        version = "0.0.1"
        summary = "kmixi2 is mixi2 library for Kotlin Multiplatform."
        homepage = "https://github.com/uakihir0/kmixi2"
        authors = "Akihiro Urushihara"
        license = "MIT"
        framework { baseName = "kmixi2" }
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
        commonMain.dependencies {
            api(project(":core"))
            api(project(":stream"))
        }
    }
}

multiplatformSwiftPackage {
    swiftToolsVersion("5.7")
    targetPlatforms {
        iOS { v("15") }
        macOS { v("12.0") }
    }
}

tasks.configureEach {
    // Fix implicit dependency between XCFramework and FatFramework tasks
    if (name.contains("assembleKmixi2") && name.contains("XCFramework")) {
        mustRunAfter(tasks.matching { it.name.contains("FatFramework") })
    }
}

tasks.podPublishXCFramework {
    doLast {
        providers.exec {
            executable = "sh"
            args = listOf(project.projectDir.path + "/../tool/rename_podfile.sh")
        }.standardOutput.asText.get()
    }
}
