package br.com.mybank.application

import android.app.Application
import br.com.mybank.api.RetrofitProvider
import br.com.mybank.di.DependencyModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


internal open class MyBankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
        setupKoin()
    }

    private fun setupKoin() =
        startKoin {
            androidLogger()
            androidContext(this@MyBankApplication)
            modules(
                DependencyModules.apiModule,
                DependencyModules.useCaseModule,
                DependencyModules.viewModelModule,
                DependencyModules.repositoryImpl,
                DependencyModules.sessionUser
            )
        }

    protected open fun initRetrofit() = RetrofitProvider.initRetrofit()
}