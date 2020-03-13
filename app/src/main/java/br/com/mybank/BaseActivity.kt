package br.com.mybank

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import br.com.mybank.util.setStatusBarColor

abstract class BaseActivity(
    @LayoutRes private val layoutId: Int? = null,
    private val statusBarColor: Int? = null
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        layoutId?.let { setContentView(it) }
        statusBarColor?.let { setStatusBarColor(statusBarColor) }
    }
}