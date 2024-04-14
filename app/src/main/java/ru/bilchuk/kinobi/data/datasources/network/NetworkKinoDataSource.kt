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

interface NetworkKinoDataSource {

    suspend fun getMovieList(page: Int): MoviesDto
    suspend fun getPosterList(movieId: Int): PostersDto
    suspend fun getReviewList(movieId: Int): ReviewsDto
}