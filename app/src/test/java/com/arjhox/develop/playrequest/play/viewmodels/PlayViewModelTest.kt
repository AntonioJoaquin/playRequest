package com.arjhox.develop.playrequest.play.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arjhox.develop.domain.common.LoadingState
import com.arjhox.develop.domain.models.RequestResponse
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.play.TrampolineSchedulerProvider
import com.arjhox.develop.playrequest.ui.common.*
import com.arjhox.develop.playrequest.ui.main.play.PlayViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert
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
    private lateinit var headersObserver: Observer<List<Header>>
    private lateinit var parameterObserver: Observer<List<Parameter>>
    private lateinit var requestResultObserver: Observer<RequestResponse>


    @Before
    fun setUp() {
        schedulerProvider = TrampolineSchedulerProvider()

        playRequestUseCase = mock()
        playViewModel = PlayViewModel(playRequestUseCase, schedulerProvider)

        loadingObserver = mock()
        playViewModel.loading.observeForever(loadingObserver)
        headersObserver = mock()
        playViewModel.headers.observeForever(headersObserver)
        parameterObserver = mock()
        playViewModel.parameters.observeForever(parameterObserver)
        requestResultObserver = mock()
        playViewModel.requestResult.observeForever(requestResultObserver)
    }


    @Test
    fun `playRequest viewModel function should call playRequest useCase function`() {
        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onSuccess(RequestResponse("", "")) })

        playViewModel.playRequest("")

        verify(playRequestUseCase).playRequest(any())
    }

    @Test
    fun `playRequest should show loading when init`() {
        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn( Single.never() )

        playViewModel.playRequest("")

        verify(loadingObserver).onChanged(eq(LoadingState.LOADING))
    }

    @Test
    fun `playRequest should show loaded when success`() {
        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onSuccess(RequestResponse("", "")) })

        playViewModel.playRequest("")

        verify(loadingObserver).onChanged(eq(LoadingState.LOADED))
    }

    @Test
    fun `playRequest should show error when error`() {
        val throwable = Throwable("Example error")

        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onError(throwable) })

        playViewModel.playRequest("")

        verify(loadingObserver).onChanged(eq(LoadingState.error(throwable.message)))
    }

    @Test
    fun `playRequest should show result when success`() {
        val result = RequestResponse("example headers", "example response")

        whenever(playRequestUseCase.playRequest(any()))
            .thenReturn(Single.create { it.onSuccess(result) })

        playViewModel.playRequest("")

        verify(requestResultObserver).onChanged(eq(result))
    }

    @Test
    fun `mapHeaders should convert headers list to a map`() {
        val headersList = listOf(
            Header("key1", "value1"),
            Header("key2", "value2")
        )
        val headersMap = mapOf(
            "key1" to "value1",
            "key2" to "value2"
        )

        val testObserver = playViewModel.mapHeaders(headersList).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertResult(headersMap)


    }

    @Test
    fun `makeRequest should create a Request object with path and headers`() {
        val path = "example path"
        val headersList = listOf(
            Header("key1", "value1"),
            Header("key2", "value2")
        )
        val request = Request(
            path,
            mapOf(
                "key1" to "value1",
                "key2" to "value2"
            )
        )

        val testObserver = playViewModel.makeRequest(path, headersList).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertResult(request)


    }


    // region Headers

    @Test
    fun `when add a new header should increment headers list`() {
        val header = Header("key", "value")
        val headerList = listOf(header)

        playViewModel.insertNewHeaderToRequest(header)

        verify(headersObserver).onChanged(eq(headerList))
    }

    @Test
    fun `when add a new header and list already contains it should not increment headers list`() {
        val header = Header("key", "value")
        val headerList = listOf(header)

        playViewModel.insertNewHeaderToRequest(header)
        playViewModel.insertNewHeaderToRequest(header)

        verify(headersObserver).onChanged(eq(headerList))
    }

    @Test
    fun `when delete a header should decrement headers list`() {
        val header1 = Header("key1", "value1")
        val header2 = Header("key2", "value2")
        val headerList = listOf(header2)

        playViewModel.insertNewHeaderToRequest(header1)
        playViewModel.insertNewHeaderToRequest(header2)
        playViewModel.deleteHeaderFromRequest(header1)

        verify(headersObserver, times(3)).onChanged(eq(headerList))
    }

    @Test
    fun `when update a header it should be updated on headers list`() {
        val header = Header("key", "value")
        val header1 = Header("key1", "value1")
        val headerItemList = HeaderItemList(header.key, header.value, 0)
        val headerList = listOf(header)

        playViewModel.insertNewHeaderToRequest(header1)
        playViewModel.updateHeaderToRequest(headerItemList)

        verify(headersObserver, times(2)).onChanged(headerList)
    }

    // endregion


    // region Parameters

    @Test
    fun `when add a new parameter should increment parameters list`() {
        val parameter = Parameter("key", "value")
        val parameterList = listOf(parameter)

        playViewModel.insertNewParameterToRequest(parameter)

        verify(parameterObserver).onChanged(eq(parameterList))
    }

    @Test
    fun `when add a new parameter and list already contains it should not increment parameters list`() {
        val parameter = Parameter("key", "value")
        val parameterList = listOf(parameter)

        playViewModel.insertNewParameterToRequest(parameter)
        playViewModel.insertNewParameterToRequest(parameter)

        verify(parameterObserver).onChanged(eq(parameterList))
    }

    @Test
    fun `when delete a parameter should decrement parameters list`() {
        val parameter1 = Parameter("key1", "value1")
        val parameter2 = Parameter("key2", "value2")
        val parameterList = listOf(parameter2)

        playViewModel.insertNewParameterToRequest(parameter1)
        playViewModel.insertNewParameterToRequest(parameter2)
        playViewModel.deleteParameterFromRequest(parameter1)

        verify(parameterObserver, times(3)).onChanged(eq(parameterList))
    }

    @Test
    fun `when update a parameter it should be updated on parameters list`() {
        val parameter = Parameter("key", "value")
        val parameter1 = Parameter("key1", "value1")
        val parameterItemList = ParameterItemList(parameter.key, parameter.value, 0)
        val parameterList = listOf(parameter)

        playViewModel.insertNewParameterToRequest(parameter1)
        playViewModel.updateParameterToRequest(parameterItemList)

        verify(parameterObserver, times(2)).onChanged(parameterList)
    }

    // endregion

}