package com.coding.newsapp.ui.search


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.domain.repository.NewsRepository
import com.coding.domain.utils.onError
import com.coding.domain.utils.onSuccess
import com.coding.newsapp.ui.utils.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var searchJob: Job? = null
    var language by  mutableStateOf<String?>("")

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    private val category: String?
        get() = state.value.selectedNewsCategory

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                searchNewsList = emptyList(),
                                errorMessage = null
                            )
                        }
                    }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchNews(category = category, searchQuery = query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: SearchScreenAction) {
        when (action) {
            is SearchScreenAction.OnArticleClick -> { /* TODO: navigation */ }

            is SearchScreenAction.OnCategoryChange -> {
                _state.update { it.copy(selectedNewsCategory = action.category) }
                if (_state.value.searchQuery.length >= 2) {
                    searchJob?.cancel()
                    searchJob = searchNews(
                        category = action.category,
                        searchQuery = _state.value.searchQuery
                    )
                }
            }

            is SearchScreenAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }

            SearchScreenAction.OnRetryClick -> {
                if (_state.value.searchQuery.length >= 2) {
                    searchJob?.cancel()
                    searchJob = searchNews(
                        category = category,
                        searchQuery = _state.value.searchQuery
                    )
                }
            }

            SearchScreenAction.OnSettingClick -> {  }
        }
    }

    private fun searchNews(category: String?, searchQuery: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        newsRepository.getSearchNews(searchQuery = searchQuery, category = category,language)
            .onSuccess { articles ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchNewsList = articles,
                        errorMessage = null
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchNewsList = emptyList(),
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}

