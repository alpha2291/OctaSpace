plugins {

    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.gms.google-services")

//    id ("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")

    id("kotlin-kapt")  // <-- ADD THIS

    id("kotlin-parcelize")
}

// Secrets are kept in local.properties (gitignored). See README for setup.
val localProps = java.util.Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}
fun localProp(key: String): String = localProps.getProperty(key, "")

android {
    namespace = "com.toletspot.houseforrent"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.toletspot.houseforrent"
        minSdk = 29
        targetSdk = 36
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Generates R.string.maps_api_key from local.properties
        resValue("string", "maps_api_key", localProp("MAPS_API_KEY"))

        externalNativeBuild {
            cmake {
                cppFlags += ""
                arguments += listOf(
                    "-DAWS_ACCESS_ID=\"${localProp("AWS_ACCESS_ID")}\"",
                    "-DAWS_SECRET_KEY=\"${localProp("AWS_SECRET_KEY")}\"",
                    "-DAWS_BUCKET_NAME=\"${localProp("AWS_BUCKET_NAME")}\"",
                    "-DCLOUDFRONT_URL=\"${localProp("CLOUDFRONT_URL")}\""
                )
            }
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "4.1.1"
        }
    }
    ndkVersion = "28.2.13676358"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.compose.runtime.saveable)
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.compose.animation.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // COIL
    implementation("io.coil-kt:coil-compose:2.7.0")

    // LOCATION ACCESS
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // NEW MEDIA3 PLAYER
    implementation("androidx.media3:media3-exoplayer:1.7.1")
    implementation("androidx.media3:media3-ui-compose:1.7.1")
    implementation("androidx.media3:media3-common:1.7.1")

    ////lottie
    implementation("com.airbnb.android:lottie-compose:6.6.7")


    //Navigation
    implementation ("androidx.navigation:navigation-compose:2.9.3")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")




    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation ("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")


    /// datastore
    implementation("androidx.datastore:datastore-preferences:1.1.7")



    // placess
    implementation("com.google.android.libraries.places:places:3.2.0")


    // map view

    implementation("com.google.maps.android:maps-compose:4.3.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")


    // serilixation
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    /// kotlin reflect for mapping api fields to string title
    implementation("org.jetbrains.kotlin:kotlin-reflect")


//AWS
    implementation ("com.amplifyframework:aws-storage-s3:1.31.3")
    implementation ("com.amplifyframework:aws-auth-cognito:1.31.3")

    //// FIREBASE

    implementation(platform("com.google.firebase:firebase-bom:34.3.0"))



    //// firebase realtime db
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")

//Push Notification
    implementation("com.google.firebase:firebase-messaging:25.0.0")
    implementation("com.google.firebase:firebase-crashlytics")

    //Update popup
    implementation("com.google.android.play:app-update:2.1.0")
    implementation("com.google.android.play:app-update-ktx:2.1.0")



    // Glide

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")







}
