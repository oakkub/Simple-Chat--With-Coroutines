package com.nimbl3

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.nimbl3.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class TemplateApplication : DaggerApplication() {

    @Inject
    lateinit var imagePipelineConfig: ImagePipelineConfig

    override fun applicationInjector(): AndroidInjector<TemplateApplication> =
        DaggerApplicationComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        plantTimber()
        setupFresco()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupFresco() {
        Fresco.initialize(this, imagePipelineConfig)
    }
}
