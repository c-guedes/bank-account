package br.com.mybank.domain.usecase

class HasLoggedInUseCase(
    private val localRepository: LocalRepositoryImpl
) {
    fun userHasLoggedIn(): Boolean = localRepository.userHasLoggedIn()

    fun setLoggedUser(logged: Boolean) {
        localRepository.setUserLoggedIng(logged)
    }
}