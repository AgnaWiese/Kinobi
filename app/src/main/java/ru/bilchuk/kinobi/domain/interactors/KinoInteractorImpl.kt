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
package ru.bilchuk.kinobi.domain.interactors

import ru.bilchuk.kinobi.domain.models.Movie
import ru.bilchuk.kinobi.domain.models.Poster
import ru.bilchuk.kinobi.domain.models.Review
import ru.bilchuk.kinobi.domain.repositories.KinoRepository
import javax.inject.Inject

class KinoInteractorImpl @Inject constructor(
    private val repository: KinoRepository
): KinoInteractor {

    override suspend fun getMovieList(page: Int): List<Movie> = repository.getMovieList(page)
    override suspend fun getPosterList(movieId: Int): List<Poster> = repository.getPosterList(movieId)
    override suspend fun getReviewList(movieId: Int): List<Review> = repository.getReviewList(movieId)
}