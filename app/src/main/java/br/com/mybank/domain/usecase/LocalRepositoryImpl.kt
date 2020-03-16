package br.com.mybank.domain.usecase

import br.com.mybank.data.SessionUtil
import br.com.mybank.data.model.UserOnboardData
import br.com.mybank.util.SharedPrefs.clearShared
import br.com.mybank.util.SharedPrefs.hasLoggedIn
import br.com.mybank.util.SharedPrefs.userOnboardData
import com.google.gson.Gson


class LocalRepositoryImpl {
    fun userHasLoggedIn(): Boolean = hasLoggedIn

    fun setUserLoggedIng(isLogged: Boolean) {
        hasLoggedIn = isLogged
    }

    fun setUserData(userData: UserOnboardData) {
        val json = Gson().toJson(userData)
        userOnboardData = json
    }

    fun getUserData(): UserOnboardData {
        return Gson().fromJson(userOnboardData, UserOnboardData::class.java)
    }

    fun clearUserData() {
        clearShared()
        SessionUtil.client = null
    }
}