plugins {
    id("com.android.application") version "8.13.1"
    id("org.jetbrains.kotlin.android") version "1.9.22"
}

android {
    namespace = "com.example.ecomarketspa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ecomarketspa"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions { jvmTarget = "17" }

    buildFeatures { compose = true }

    // ✅ Compiler compatible con Material3 + Animation
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    /* =============== CORE =============== */
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    /* ============ COMPOSE (alineado) ============ */
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.runtime:runtime")

    /* ============ NAVIGATION ============ */
    implementation("androidx.navigation:navigation-compose:2.7.6")

    /* ============ VIEWMODEL ============ */
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    /* ============ ROOM ============ */
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    /* ============ NETWORK ============ */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    /* ============ IMAGES ============ */
    implementation("io.coil-kt:coil-compose:2.6.0")

    /* ============ COROUTINES ============ */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    /* ============ DATASTORE ============ */
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    /* ============ DEBUG ============ */
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

/* =========================================================
   🔥 FORCE COMPOSE
   Evita que entren versiones viejas por transitividad
   ========================================================= */
configurations.all {
    resolutionStrategy {
        force(
            "androidx.compose.ui:ui:1.5.4",
            "androidx.compose.runtime:runtime:1.5.4",
            "androidx.compose.animation:animation:1.5.4",
            "androidx.compose.animation:animation-core:1.5.4"
        )
    }
}


