object TestDependencies {
    private object Versions {
        const val junit = "4.13"
        const val junitExt = "1.1.2"
        const val espresso = "3.3.0"
        const val assertjCore = "3.16.1"
        const val mockitoCore = "3.5.2"
        const val mockitoKotlin = "2.1.0"
        const val testRunner = "1.3.0-rc03"
        const val testRules = "1.3.0-rc03"
        const val coroutinesTest = "1.3.9"
    }

    const val junit = "junit:junit:${Versions.junit}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"

    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val assertjCore = "org.assertj:assertj-core:${Versions.assertjCore}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"

    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"

    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
}
