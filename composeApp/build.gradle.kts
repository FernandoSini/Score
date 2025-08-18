import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.room)
    id("com.google.devtools.ksp")
}
room {
    schemaDirectory("$projectDir/schemas")
}
kotlin {
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class) compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            javaParameters.set(true)

        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class) instrumentedTestVariant.sourceSetTree.set(
            KotlinSourceSetTree.test
        )
        @OptIn(ExperimentalKotlinGradlePluginApi::class) instrumentedTestVariant {
            dependencies {
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.ui.test.junit4)
                implementation(libs.androidx.test.junit)
                debugImplementation(libs.androidx.test.manifest)
            }
        }
    }
    tasks.withType<Test> {
        if (name == "compileDebugAndroidTestKotlinAndroid") {
            enabled = false

        }
        useJUnitPlatform()
        enabled = true

        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            //implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.android)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.android.billing)

        }
        commonMain {
            resources.srcDir("src/commonMain/composeResources")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.google.material3)
            implementation(libs.androidx.navigation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.websockets)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.room.runtime)
            implementation(libs.coil.svg)
            implementation(libs.coil3.coil.compose)
            implementation(libs.coil3.coil.network.ktor3)
            implementation(libs.sqlite.bundled)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        commonTest {
            resources.srcDir("src/commonTest/composeResources")
        }
    }
}

android {
    namespace = "com.flemis.score"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    testOptions {
        unitTests {
            all {
                it.exclude("**/screen/**")
            }
        }
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applicationId = "com.flemis.score"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    applicationVariants.all { variant ->
        variant.resValue("string", "versionName", variant.versionName)
        true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    testImplementation(libs.koin.test)
// Koin for JUnit 4
    testImplementation(libs.koin.test.junit4)
// Koin for JUnit 5
    testImplementation(libs.koin.test.junit5)
    debugImplementation(compose.uiTooling)
    listOf(
        "kspAndroid", "kspIosSimulatorArm64", "kspIosX64", "kspIosArm64",// "kspJvm",
    ).forEach {
        add(it, libs.room.compiler)

    }
}

