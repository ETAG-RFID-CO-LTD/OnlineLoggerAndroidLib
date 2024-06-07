plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    signingConfigs {
        create("release") {
        }
    }
    namespace = "com.etag.onlinelogger"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    val httpLoggingInterceptor = "4.11.0"
    val retrofit = "2.9.0"
    implementation("com.squareup.okhttp3:logging-interceptor:$httpLoggingInterceptor")
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
}

publishing {
    publications {
        create<MavenPublication>("release") {
//            from(components["release"]) // This should work if 'release' is correctly defined
            artifact("$buildDir/outputs/aar/onlinelogger-release.aar")

            groupId = "com.etag"
            artifactId = "onlinelogger"
            version = "1.0.0"

            pom {
                name.set("OnlineLogger")
                description.set("A useful library for logging online.")
                url.set("https://github.com/ETAG-RFID-CO-LTD/OnlineLoggerAndroidLib")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("Than@e-tagrfid.com")
                        name.set("Than Zaw Hein")
                        email.set("Than@e-tagrfid.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/ETAG-RFID-CO-LTD/OnlineLoggerAndroidLib.git")
                    developerConnection.set("scm:git:ssh://github.com/ETAG-RFID-CO-LTD/OnlineLoggerAndroidLib.git")
                    url.set("https://github.com/ETAG-RFID-CO-LTD/OnlineLoggerAndroidLib.git")
                }
            }
        }
    }

    repositories {
        mavenLocal()
    }
}
