package com.coding.newsapp.di


import com.coding.newsapp.ui.article_detail.ArticleDetailViewModel
import com.coding.newsapp.ui.bookmark.BookMarkViewModel
import com.coding.newsapp.ui.headline.HeadLineViewModel
import com.coding.newsapp.ui.search.SearchViewModel
import com.coding.newsapp.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HeadLineViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { BookMarkViewModel(get()) }
    viewModel { ArticleDetailViewModel(get()) }

}
