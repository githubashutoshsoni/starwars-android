package com.starwars.android.dependencyinjection

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GameApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO
