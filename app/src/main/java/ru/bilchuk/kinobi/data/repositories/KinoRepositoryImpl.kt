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
package ru.bilchuk.kinobi.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.bilchuk.kinobi.data.datasources.network.NetworkKinoDataSource
import ru.bilchuk.kinobi.data.mappers.MovieMapper
import ru.bilchuk.kinobi.data.mappers.PosterMapper
import ru.bilchuk.kinobi.data.mappers.ReviewMapper
import ru.bilchuk.kinobi.domain.models.Movie
import ru.bilchuk.kinobi.domain.models.Poster
import ru.bilchuk.kinobi.domain.models.Review
import ru.bilchuk.kinobi.domain.repositories.KinoRepository
import javax.inject.Inject

class KinoRepositoryImpl @Inject constructor(
    private val dataSource: NetworkKinoDataSource,
    private val movieMapper: MovieMapper,
    private val posterMapper: PosterMapper,
    private val reviewMapper: ReviewMapper,
    private val dispatcher: CoroutineDispatcher
): KinoRepository {

    override suspend fun getMovieList(page: Int): List<Movie> = withContext(dispatcher) {
        dataSource.getMovieList(page).docs?.map {
            movieMapper.convert(it)
        } ?: emptyList()
    }

    override suspend fun getPosterList(movieId: Int): List<Poster> = withContext(dispatcher) {
        dataSource.getPosterList(movieId).docs?.map {
            posterMapper.convert(it)
        } ?: emptyList()
    }

    override suspend fun getReviewList(movieId: Int): List<Review> = withContext(dispatcher) {
        dataSource.getReviewList(movieId).docs?.map {
            reviewMapper.convert(it)
        } ?: emptyList()
    }
}