package com.arjhox.develop.playrequest.play.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.ui.main.play.PlayFragment
import com.arjhox.develop.playrequest.ui.main.play.PlayViewModel
import com.arjhox.develop.playrequest.ui.main.play.playModule
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.KoinContextHandler.getOrNull
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Config.OLDEST_SDK])
@RunWith(RobolectricTestRunner::class)
class PlayFragmentTest: KoinTest {

    private lateinit var playRequestUseCase: PlayRequestUseCase
    private val viewModel: PlayViewModel by inject()


    @Before
    fun setUp() {
        if (getOrNull() == null) {
            startKoin {
                androidLogger()
                androidContext(InstrumentationRegistry.getInstrumentation().context)
                modules(playModule)
            }
        }

        playRequestUseCase = mock()
    }


    @Test
    fun `when the fragment is initialized playButton should be hidden`() {
        launchFragmentInContainer<PlayFragment>()

        onView(withId(R.id.floatingActionButtonPlayAction))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun `when write a request playButton should be shown`() {
        launchFragmentInContainer<PlayFragment>()

        onView(withId(R.id.textInputEditTextRequestPath))
            .perform(setTextInTextView("New Request"))

        onView(withId(R.id.floatingActionButtonPlayAction))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun `when request box is empty playButton should be hidden`() {
        launchFragmentInContainer<PlayFragment>()

        onView(withId(R.id.textInputEditTextRequestPath))
            .perform(setTextInTextView("New request"))
        onView(withId(R.id.textInputEditTextRequestPath))
            .perform(setTextInTextView(""))

        onView(withId(R.id.floatingActionButtonPlayAction))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }


    @After
    fun stopKoinAfterTest() = stopKoin()

}