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
package ru.bilchuk.kinobi.di

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bilchuk.kinobi.data.datasources.network.NetworkKinoDataSource
import ru.bilchuk.kinobi.data.datasources.network.NetworkKinoDataSourceImpl
import ru.bilchuk.kinobi.data.mappers.MovieMapper
import ru.bilchuk.kinobi.data.mappers.MovieMapperImpl
import ru.bilchuk.kinobi.data.mappers.PosterMapper
import ru.bilchuk.kinobi.data.mappers.PosterMapperImpl
import ru.bilchuk.kinobi.data.mappers.ReviewMapper
import ru.bilchuk.kinobi.data.mappers.ReviewMapperImpl
import ru.bilchuk.kinobi.data.network.KinoRetrofitConstants
import ru.bilchuk.kinobi.data.network.KinoService
import ru.bilchuk.kinobi.data.repositories.KinoRepositoryImpl
import ru.bilchuk.kinobi.domain.interactors.KinoInteractor
import ru.bilchuk.kinobi.domain.interactors.KinoInteractorImpl
import ru.bilchuk.kinobi.domain.repositories.KinoRepository


@Module
abstract class MovieModule {

    companion object {
        @Provides
        fun provideRetrofitMovieService(gson: Gson): KinoService = Retrofit.Builder()
            .baseUrl(KinoRetrofitConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient.Builder().apply {
                addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader("X-API-KEY", KinoRetrofitConstants.API_KEY)
                        .build()
                    it.proceed(request)
                }
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(loggingInterceptor)
            }.build())
            .build()
            .create(KinoService::class.java)
    }

    @Binds
    abstract fun bindNetworkMovieDataSource(impl: NetworkKinoDataSourceImpl): NetworkKinoDataSource

    @Binds
    abstract fun bindMovieMapper(impl: MovieMapperImpl): MovieMapper

    @Binds
    abstract fun bindPosterMapper(impl: PosterMapperImpl): PosterMapper

    @Binds
    abstract fun bindReviewMapper(impl: ReviewMapperImpl): ReviewMapper

    @Binds
    abstract fun bindMovieRepository(impl: KinoRepositoryImpl): KinoRepository

    @Binds
    abstract fun bindMovieInteractor(impl: KinoInteractorImpl): KinoInteractor
}