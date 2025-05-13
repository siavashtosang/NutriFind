package com.example.nutrifind.ui.features.home


import com.example.nutrifind.MainDispatcherRule
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.repositories.FakeNutriFindRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepo: FakeNutriFindRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {

        fakeRepo = FakeNutriFindRepository()
        viewModel = HomeViewModel(fakeRepo)
    }

    @Test
    fun initialStateIsLoading() {
        //immediately after init
        assertTrue(viewModel.uiState.value.suggestionResults is DataResponse.Loading)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadHomeDataEmitsSuccessForSuggestionResults() = runTest(mainDispatcherRule.dispatcher) {

        fakeRepo.setShouldReturnNetWorkError(false)

        advanceUntilIdle()

        val result = viewModel.uiState.value.suggestionResults
        assertTrue(result is DataResponse.Success)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadHomeDataEmitsErrorWhenRepositoryFails() = runTest(mainDispatcherRule.dispatcher) {

        fakeRepo.setShouldReturnNetWorkError(true)

        viewModel.onRetryHome()

        advanceUntilIdle()

        val result = viewModel.uiState.value.suggestionResults
        assertTrue(result is DataResponse.Error)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchTriggeredUpdatesSearchedResults() = runTest(mainDispatcherRule.dispatcher) {
        //search pasta search
        fakeRepo.setShouldReturnNetWorkError(false)
        viewModel.onSearchTextChange("pasta")
        viewModel.onSearchTriggered()

        advanceUntilIdle()

        val searched = viewModel.uiState.value.searchedResults
        assertTrue(searched is DataResponse.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun teardown() {
        Dispatchers.resetMain()
    }
}