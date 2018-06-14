package com.nimbl3.di

import com.nimbl3.TemplateApplication
import com.nimbl3.di.modules.ActivityBuilderModule
import com.nimbl3.di.modules.AppModule
import com.nimbl3.di.modules.FrescoModule
import com.nimbl3.di.modules.GsonModule
import com.nimbl3.di.modules.OkHttpClientModule
import com.nimbl3.di.modules.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    AppModule::class,
    GsonModule::class,
    FrescoModule::class,
    OkHttpClientModule::class,
    ActivityBuilderModule::class
])
interface ApplicationComponent : AndroidInjector<TemplateApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TemplateApplication>()
}
