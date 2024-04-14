/*
 * Copyright (C) 2024. Jane Bilchuk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.bilchuk.kinobi.presentation.movies.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bilchuk.kinobi.domain.interactors.KinoInteractor
import ru.bilchuk.kinobi.domain.models.Movie
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val interactor: KinoInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    fun getMovieListFirstPage() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                val movies = interactor.getMovieList()
                _uiState.update {
                    it.copy(
                        movies = movies,
                        moviesNextChunk = null,
                        isLoading = false,
                        pagesLoaded = 1,
                    )
                }
            } catch (e: Exception) {
                Log.d("MovieListViewModel", e.message.toString())
                _uiState.update {
                    it.copy(
                        isError = true,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun getMovieListNextPage() {
        viewModelScope.launch {
            try {
                val movies = interactor.getMovieList(_uiState.value.pagesLoaded + 1)
                _uiState.update {
                    it.copy(
                        movies = it.movies.toMutableList().apply{ addAll(movies) },
                        moviesNextChunk = movies,
                        pagesLoaded = it.pagesLoaded + 1,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                Log.d("MovieListViewModel", e.message.toString())
                _uiState.update {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        moviesNextChunk = null,
                    )
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update {
            it.copy(
                isError = false
            )
        }
    }

    data class MovieListUiState(
        val isError: Boolean = false,
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
        val moviesNextChunk: List<Movie>? = null,
        val pagesLoaded: Int = 0,
    )
}