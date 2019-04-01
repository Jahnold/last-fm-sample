@file:Suppress("MayBeConstant")

object Versions {

    // Build
    //~~~~~~

    val gradle = "3.3.2"
    val buildTools = "28.0.3"
    val minSdk = 21
    val targetSdk = 28

    // Main
    //~~~~~

    object Kotlin {
        val core = "1.3.20"
        val ktx = "1.0.0"
    }

    val constraint = "1.1.3"

    object AndroidX {
        val design = "1.0.0-rc01"
        val compat = "1.0.0"
        val app = "1.0.0"
        val cardView = "1.0.0"
        val recyclerView = "1.0.0"
        val fragment = "1.0.0"
        val constraint = "1.1.3"
        val multidex = "2.0.0"
        val archLifecycle = "2.0.0-rc01"
    }

    val dagger = "2.21"
    val inject = "1"

    object Rx {
        val java = "2.2.0"
        val android = "2.1.0"
    }

    val retrofit = "2.5.0"
    val okHttp = "3.12.0"
    val picasso = "2.71828"

    // Testing
    //~~~~~~~~

    val jUnit = "4.12"
    val truth = "0.40"
    val mockito = "2.10.0"
    val mockitoKotlin = "1.5.0"
    val espresso = "2.2.2"
}

object Libs {

    // Build
    //~~~~~~

    object Build {
        val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.core}"
    }

    // Main
    //~~~~~

    object Kotlin {
        val core = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin.core}"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.Kotlin.core}"
        val ktx = "androidx.core:core-ktx:${Versions.Kotlin.ktx}"
    }

    object AndroidX {
        val design = "com.google.android.material:material:${Versions.AndroidX.design}"
        val compat = "androidx.core:core:${Versions.AndroidX.compat}"
        val app = "androidx.appcompat:appcompat:${Versions.AndroidX.app}"
        val cardView = "androidx.cardview:cardview:${Versions.AndroidX.cardView}"
        val recyclerView = "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerView}"
        val fragment = "androidx.fragment:fragment:${Versions.AndroidX.fragment}"
        val constraint = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraint}"
    }

    object ArchComponents {
        val viewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.AndroidX.archLifecycle}"
        val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.AndroidX.archLifecycle}"
        val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.archLifecycle}"
    }

    object Dagger {
        val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        val android = "com.google.dagger:dagger-android:${Versions.dagger}"
        val support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        val processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
        val inject = "javax.inject:javax.inject:${Versions.inject}"
    }

    object Retrofit {
        val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        val rx2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    }

    object OkHttp {
        val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    }

    object Rx {
        val java = "io.reactivex.rxjava2:rxjava:${Versions.Rx.java}"
        val android = "io.reactivex.rxjava2:rxandroid:${Versions.Rx.android}"
    }

    val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    // Testing
    //~~~~~~~~

    val jUnit = "junit:junit:${Versions.jUnit}"
    val truth = "com.google.truth:truth:${Versions.truth}"

    object Mockito {
        val core = "org.mockito:mockito-core:${Versions.mockito}"
        val inline = "org.mockito:mockito-inline:${Versions.mockito}"
        val kotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
    }
}