package br.com.mybank.application

import android.app.Application
import br.com.mybank.api.RetrofitProvider
import br.com.mybank.di.DependencyModules
import br.com.mybank.util.SharedPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


internal open class MyBankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
        setupPrefs()
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
                DependencyModules.sessionUser,
                DependencyModules.preferences
            )
        }

    private fun setupPrefs() = SharedPrefs.iniSharedPrefs(this)

    protected open fun initRetrofit() = RetrofitProvider.initRetrofit()

}