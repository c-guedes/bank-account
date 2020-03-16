package br.com.mybank.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


object SharedPrefs {
    private lateinit var mSharedPref: SharedPreferences

//    var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    var userOnboardData: String?
        get() = mSharedPref.getString("userOnboardData", null)
        set(value) = mSharedPref.edit().putString("userOnboardData", value).apply()
    var hasLoggedIn: Boolean
        get() = mSharedPref.getBoolean("hasLoggedIn", false)
        set(value) = mSharedPref.edit().putBoolean("hasLoggedIn", value).apply()

    fun clearShared() {
        hasLoggedIn = false
        userOnboardData = null
    }

    fun iniSharedPrefs(context: Context) {
//        mSharedPref = EncryptedSharedPreferences.create(
//            "secret_shared_prefs",
//            masterKeyAlias,
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    }
}