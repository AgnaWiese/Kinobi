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
package ru.bilchuk.kinobi.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.bilchuk.kinobi.data.models.network.MoviesDto
import ru.bilchuk.kinobi.data.models.network.PostersDto
import ru.bilchuk.kinobi.data.models.network.ReviewsDto

interface KinoService {

    @GET("/v1.4/movie")
    suspend fun getMovieList(@Query("page") page: Int, @Query("limit") limit: Int): MoviesDto

    @GET("/v1.4/image")
    suspend fun getPosterList(@Query("movieId") movieId: Int, @Query("limit") limit: Int): PostersDto

    @GET("/v1.4/review")
    suspend fun getReviewList(@Query("movieId") movieId: Int, @Query("limit") limit: Int): ReviewsDto
}