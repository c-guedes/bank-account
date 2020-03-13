package br.com.mybank.di

import br.com.mybank.api.RetrofitProvider
import br.com.mybank.data.BankApi
import br.com.mybank.data.BankApiRepositoryImpl
import br.com.mybank.data.SessionUtil
import br.com.mybank.domain.usecase.LoginUseCase
import br.com.mybank.domain.usecase.PaymentsUseCase
import br.com.mybank.ui.dashboard.CurrencyViewModel
import br.com.mybank.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {
    val useCaseModule = module {
        single { LoginUseCase(get()) }
        single { PaymentsUseCase(get()) }
    }

    val sessionUser = module {
        single { SessionUtil }
    }

    val viewModelModule = module {
        viewModel {
            LoginViewModel(get())
        }

        viewModel {
            CurrencyViewModel(get())
        }
    }

    val repositoryImpl = module {
        single { BankApiRepositoryImpl(api = get(), context = get()) }
    }

    val apiModule = module {
        fun provideRetrofit(): BankApi {
            return RetrofitProvider.providesRetrofitApi(BankApi::class.java)
        }
        single { provideRetrofit() }
    }
}