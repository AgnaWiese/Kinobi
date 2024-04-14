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
package ru.bilchuk.kinobi.presentation.movies.reviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.bilchuk.kinobi.domain.interactors.KinoInteractor
import ru.bilchuk.kinobi.domain.models.Review
import javax.inject.Inject

class ReviewListViewModel @Inject constructor(
    private val interactor: KinoInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewListUiState())
    val uiState: StateFlow<ReviewListUiState> = _uiState.asStateFlow()

    fun getReviewList(movieId: Int) {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                val review = interactor.getReviewList(movieId)
                _uiState.update {
                    it.copy(
                        review = review,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                Log.d("ReviewListViewModel", e.message.toString())
                _uiState.update {
                    it.copy(
                        isError = true,
                        isLoading = false,
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

    data class ReviewListUiState(
        val isError: Boolean = false,
        val isLoading: Boolean = false,
        val review: List<Review> = emptyList()
    )
}