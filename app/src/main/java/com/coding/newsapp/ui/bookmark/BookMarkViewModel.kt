package com.coding.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.domain.model.Article
import com.coding.domain.repository.NewsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookMarkViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var searchJob: Job? = null
    private var observeBookMarkJob: Job? = null

    private val _state = MutableStateFlow(BookMarkState())
    val state: StateFlow<BookMarkState> = _state
        .onStart {
            observeAllBookMarkNews()
            observeSearchQuery()



        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: BookMarkAction) {
        when (action) {
            is BookMarkAction.OnArticleClick -> {}
            BookMarkAction.OnRetryClick -> observeAllBookMarkNews()
            BookMarkAction.OnSettingClick -> {}

            is BookMarkAction.OnArticlesListDelete -> {
                deleteArticlesList(action.articles)
            }

            is BookMarkAction.OnDeleteClick -> {
                deleteArticle(action.article)
            }

            is BookMarkAction.OnSearchIconClick -> {
                _state.update { it.copy(isSearchBarVisible = true) }
            }

            is BookMarkAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }

            BookMarkAction.OnSearchClosedIconClick -> {
                _state.update {
                    it.copy(isSearchBarVisible = false)
                }
            }
        }
    }

    private fun observeSerBookMarkNewsAsFlow(flow: Flow<List<Article>>): Job {
        return flow
            .onStart {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            }
            .onEach { articles ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchNewsList = articles,
                        errorMessage = null
                    )
                }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
            .launchIn(viewModelScope)
    }
    private fun observeBookMarkNewsAsFlow(flow: Flow<List<Article>>): Job {
        return flow
            .onStart {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            }
            .onEach { articles ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        bookMarList = articles,
                        errorMessage = null
                    )
                }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
            .launchIn(viewModelScope)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun deleteArticlesList(articles: List<Article>) = viewModelScope.launch {
        newsRepository.deleteArticlesList(articles)
    }

    fun observeAllBookMarkNews() {
        restartObservation(newsRepository.getAllArticles())
    }

    fun observeBookMarkNewsByPriority(priority: String) {
        restartObservation(newsRepository.getArticlesByPriority(priority))
    }

    fun searchBookMarkNewsByTitle(title: String) {
        searchJob?.cancel()
        searchJob= observeSerBookMarkNewsAsFlow(newsRepository.searchArticles(title))
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        observeAllBookMarkNews()
                    }
                    query.length >= 2 -> {
                       searchBookMarkNewsByTitle(query)

                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun restartObservation(flow: Flow<List<Article>>) {
        observeBookMarkJob?.cancel()
        observeBookMarkJob = observeBookMarkNewsAsFlow(flow)
    }
}
