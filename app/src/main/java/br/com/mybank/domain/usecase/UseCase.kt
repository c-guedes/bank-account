package br.com.mybank.domain.usecase

import io.reactivex.Single

interface UseCase<T, in Param> {
    fun execute(params: Param): Single<T>
}