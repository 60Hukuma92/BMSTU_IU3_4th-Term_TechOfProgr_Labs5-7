package com.test.magicalhaven

import android.app.Application
import com.test.magicalhaven.di.AppComponent
import com.test.magicalhaven.di.AppModule
import com.test.magicalhaven.di.DaggerAppComponent //

class MagicalHavenApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}