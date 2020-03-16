package br.com.mybank

import android.os.Build
import br.com.mybank.domain.usecase.HasLoggedInUseCase
import br.com.mybank.domain.usecase.LocalRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class HasLoggedInUseCaseTest {
    private val localRepositoryImpl = mockk<LocalRepositoryImpl>(relaxed = true)

    private val useCase = HasLoggedInUseCase(
        localRepositoryImpl
    )

    @Test
    fun `on first login the loggedIn value should be false`() {
        Assert.assertEquals(false, useCase.userHasLoggedIn())
    }

    @Test
    fun testSecondLogin() {
        every {
            useCase.userHasLoggedIn()
        } returns true

        Assert.assertEquals(true, useCase.userHasLoggedIn())
    }

}