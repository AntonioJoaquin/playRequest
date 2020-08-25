package com.arjhox.develop.playrequest.play.ui.play

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.play.ui.setTextInTextView
import com.arjhox.develop.playrequest.ui.common.Header
import com.arjhox.develop.playrequest.ui.main.play.PlayFragment
import com.arjhox.develop.playrequest.ui.main.play.PlayFragmentDirections
import com.arjhox.develop.playrequest.ui.main.play.PlayViewModel
import com.arjhox.develop.playrequest.ui.main.play.playModule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@Config(sdk = [Config.OLDEST_SDK])
@RunWith(RobolectricTestRunner::class)
class PlayFragmentTest: KoinTest {

    private val viewModel: PlayViewModel by inject()

    private lateinit var playRequestUseCase: PlayRequestUseCase
    private lateinit var navController: NavController


    @Before
    fun setUp() {
        if (KoinContextHandler.getOrNull() == null) {
            startKoin {
                androidLogger()
                androidContext(InstrumentationRegistry.getInstrumentation().context)
                modules(playModule)
            }
        }

        playRequestUseCase = mock()
        navController = mock()

        launchFragmentInContainer {
            PlayFragment().also {
                it.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(it.requireView(), navController)
                    }
                }
            }
        }
    }


    @Test
    fun `when the fragment is initialized playButton should be hidden`() {
        onView(withId(R.id.floatingActionButtonPlayAction))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun `when write a request playButton should be shown`() {
        onView(withId(R.id.textInputEditTextRequestPath))
            .perform(setTextInTextView("New Request"))

        onView(withId(R.id.floatingActionButtonPlayAction))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun `when request box is empty playButton should be hidden`() {
        onView(withId(R.id.textInputEditTextRequestPath))
            .perform(setTextInTextView("New request"))
        onView(withId(R.id.textInputEditTextRequestPath))
            .perform(setTextInTextView(""))

        onView(withId(R.id.floatingActionButtonPlayAction))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun `when click on ADD_HEADERS should open header dialog`() {
        val action = PlayFragmentDirections.openHeaderDialogAction(Header())

        onView(withId(R.id.buttonAddHeader))
            .perform(ViewActions.click())
        verify(navController).navigate(action)
    }


    @After
    fun stopKoinAfterTest() = stopKoin()

}