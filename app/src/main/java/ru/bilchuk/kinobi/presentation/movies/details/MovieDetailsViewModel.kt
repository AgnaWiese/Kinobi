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
package ru.bilchuk.kinobi.presentation.movies.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bilchuk.kinobi.domain.interactors.KinoInteractor
import ru.bilchuk.kinobi.domain.models.Poster
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val interactor: KinoInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(PosterListUiState())
    val uiState: StateFlow<PosterListUiState> = _uiState.asStateFlow()

    fun getPosterList(movieId: Int) {
        viewModelScope.launch {
            try {
                val poster = interactor.getPosterList(movieId)
                _uiState.update {
                    it.copy(
                        poster = poster
                    )
                }
            } catch (e: Exception) {
                Log.d("MovieDetailsViewModel", e.message.toString())
                _uiState.update {
                    it.copy(
                        isError = true
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

    data class PosterListUiState(
        val isError: Boolean = false,
        val poster: List<Poster> = emptyList()
    )
}