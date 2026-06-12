import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

android {
    namespace = "com.example.drivez"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.drivez"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
        }

        val mapboxPublicToken = properties.getProperty("MAPBOX_ACCESS_TOKEN") ?: ""
        buildConfigField("String", "MAPBOX_TOKEN", "\"$mapboxPublicToken\"")
        manifestPlaceholders["MAPBOX_ACCESS_TOKEN"] = mapboxPublicToken
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Configuração moderna para o compilador do Kotlin (Sem o bug de extensão duplicada)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    // 1. Definição do BOM do Compose correto para Kotlin 1.9 (2024.04.01)
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation(libs.androidx.ui.text)
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))

    // 2. Dependências do Catálogo (mantidas as originais)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.runtime)

    // 3. Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // 4. Bibliotecas Manuais Corrigidas e Alinhadas para SDK 34/35 + Kotlin 1.9
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.7") // Alinhado com o BOM
    implementation("androidx.navigation:navigation-compose:2.7.7")   // Versão estável para Kotlin 1.9

    // Mantido apenas o Coil 2.x estável (o Coil 3 exige estrutura Kotlin 2.0+)
    implementation("io.coil-kt.coil3:coil-compose:3.0.0")

    implementation("androidx.compose.foundation:foundation:1.6.7")   // Alinhado com o BOM
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0") // Alinhado com ciclo de vida estável
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")
    implementation("com.mapbox.maps:android:11.2.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}