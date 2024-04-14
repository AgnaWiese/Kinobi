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
package ru.bilchuk.kinobi.data.datasources.network

import ru.bilchuk.kinobi.data.models.network.MoviesDto
import ru.bilchuk.kinobi.data.models.network.PostersDto
import ru.bilchuk.kinobi.data.models.network.ReviewsDto
import ru.bilchuk.kinobi.data.network.KinoService
import javax.inject.Inject

class NetworkKinoDataSourceImpl @Inject constructor(
    private val kinoService: KinoService,
): NetworkKinoDataSource {

    override suspend fun getMovieList(page: Int): MoviesDto = kinoService.getMovieList(
        page = page,
        limit = DEFAULT_LIMIT
    )
    override suspend fun getPosterList(movieId: Int): PostersDto = kinoService.getPosterList(
        movieId = movieId,
        limit = DEFAULT_LIMIT
    )
    override suspend fun getReviewList(movieId: Int): ReviewsDto = kinoService.getReviewList(
        movieId = movieId,
        limit = DEFAULT_LIMIT
    )

    companion object {
        const val DEFAULT_LIMIT = 20
    }
}