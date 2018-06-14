package com.nimbl3.di.modules

import android.app.Application
import android.content.Context
import com.facebook.common.internal.Suppliers
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class FrescoModule {

    @Provides
    fun provideImagePipeline(context: Context, okHttpClient: OkHttpClient): ImagePipelineConfig {
        return OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient)
            .setDownsampleEnabled(true)
            .experiment()
            .setBitmapPrepareToDraw(true, 0, Int.MAX_VALUE, true)
            .experiment()
            .setSmartResizingEnabled(Suppliers.BOOLEAN_TRUE)
            .build()
    }

}
