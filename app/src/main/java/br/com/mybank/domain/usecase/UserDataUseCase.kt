package br.com.mybank.domain.usecase

import br.com.mybank.data.model.UserOnboardData

class UserDataUseCase(
    private val localRepositoryImpl: LocalRepositoryImpl
) {
    fun setUserData(userOnboardData: UserOnboardData) {
        localRepositoryImpl.setUserData(userOnboardData)
    }

    fun getUserData(): UserOnboardData = localRepositoryImpl.getUserData()

    fun clearUserData() {
        localRepositoryImpl.clearUserData()
    }
}