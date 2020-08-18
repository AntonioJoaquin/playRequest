package com.arjhox.develop.playrequest.play.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arjhox.develop.domain.common.LoadingState
import com.arjhox.develop.domain.models.RequestResponse
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.play.TrampolineSchedulerProvider
import com.arjhox.develop.playrequest.ui.main.play.PlayViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlayViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()


    private lateinit var schedulerProvider: TrampolineSchedulerProvider

    private lateinit var playRequestUseCase: PlayRequestUseCase
    private lateinit var playViewModel: PlayViewModel

    private lateinit var loadingObserver: Observer<LoadingState>
    private lateinit var requestResultObserver: Observer<RequestResponse>


    @Before
    fun setUp() {
        schedulerProvider = TrampolineSchedulerProvider()

        playRequestUseCase = mock()
        playViewModel = PlayViewModel(playRequestUseCase, schedulerProvider)

        loadingObserver = mock()
        playViewModel.loading.observeForever(loadingObserver)
        requestResultObserver = mock()
        playViewModel.requestResult.observeForever(requestResultObserver)
    }


    @Test
    fun `playRequest viewModel function should call playRequest useCase function`() {
        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onSuccess(RequestResponse("", "")) })

        playViewModel.playRequest()

        verify(playRequestUseCase).playRequest(any())
    }

    @Test
    fun `playRequest should show loading when init`() {
        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn( Single.never() )

        playViewModel.playRequest()

        verify(loadingObserver).onChanged(eq(LoadingState.LOADING))
    }

    @Test
    fun `playRequest should show loaded when success`() {
        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onSuccess(RequestResponse("", "")) })

        playViewModel.playRequest()

        verify(loadingObserver).onChanged(eq(LoadingState.LOADED))
    }

    @Test
    fun `playRequest should show error when error`() {
        val throwable = Throwable("Example error")

        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onError(throwable) })

        playViewModel.playRequest()

        verify(loadingObserver).onChanged(eq(LoadingState.error(throwable.message)))
    }

    @Test
    fun `playRequest should show result when success`() {
        val result = RequestResponse("example headers", "example response")

        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onSuccess(result) })

        playViewModel.playRequest()

        verify(requestResultObserver).onChanged(eq(result))
    }

}