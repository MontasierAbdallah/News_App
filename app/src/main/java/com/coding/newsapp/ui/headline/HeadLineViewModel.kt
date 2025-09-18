package com.coding.newsapp.ui.headline



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.domain.repository.NewsRepository
import com.coding.domain.utils.onError
import com.coding.domain.utils.onSuccess

import com.coding.newsapp.ui.utils.toUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class HeadLineViewModel(
    private val newsRepository: NewsRepository,

) : ViewModel() {

    private var newsJob: Job? = null
    var language by  mutableStateOf<String?>("")




    private val _state = MutableStateFlow(HeadLineState())
    val state = _state.onStart {
        getHeadLineNews(_state.value.selectedCategory,language)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    fun onAction(action: HeadLineAction) {
        when (action) {
            is HeadLineAction.OnArticleClick -> {
                // Navigate or show article details
            }

            is HeadLineAction.OnCategoryClick -> {
                _state.update { it.copy(selectedCategory = action.category) }
                getHeadLineNews(action.category,language)
            }

            HeadLineAction.OnSettingClick -> {
                // Open settings
            }

            HeadLineAction.OnRetryGeneralClick -> {
                // Retry with initial category
                getHeadLineNews(_state.value.selectedCategory,language)
            }

            HeadLineAction.OnRetryCategoryClick -> {
                // Retry with selected category if available
                getHeadLineNews(_state.value.selectedCategory,language)
            }
        }
    }

    private fun getHeadLineNews(category: String?,language: String?) {
        newsJob?.cancel()
        newsJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            newsRepository.getHeadLineNews(category,language)
                .onSuccess { articles ->
                    _state.update {
                        it.copy(
                            newsList = articles,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            newsList = emptyList(),
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
    }
}




