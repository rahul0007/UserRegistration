import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {

    //Dependency Injection
    private val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    private val hiltAndroidCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"

// room

    private val androidRoom = "androidx.room:room-ktx:${Versions.roomVersion}"
    private val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    //    glide
    private val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    private val glideAnnotation = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    //  Lifecycle
    private val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    private val lifecycleLivedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"

    private val lifecycleProVersion =
        "androidx.lifecycle:lifecycle-process:${Versions.lifecycleProVersion}"
    private val lifecycleRuntimeVersion =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"

    //    Navigation
    private val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    private val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"

    //android UI
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private val materialApp = "com.google.android.material:material:${Versions.materialApp}"

    //test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val lifecycleLibrary = arrayListOf<String>().apply {
        add(lifecycleViewModel)
        add(lifecycleLivedata)
        add(lifecycleRuntimeVersion)
        add(lifecycleProVersion)

    }

    val navigationLibrary = arrayListOf<String>().apply {
        add(navigationFragment)
        add(navigationUi)
    }

    val appLibraries = arrayListOf<String>().apply {
        add(appcompat)
        add(coreKtx)
        add(materialApp)
        add(constraintLayout)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}
