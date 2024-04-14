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
package ru.bilchuk.kinobi.data.mappers

import ru.bilchuk.kinobi.data.models.network.MovieDto
import ru.bilchuk.kinobi.domain.models.Backdrop
import ru.bilchuk.kinobi.domain.models.Movie
import ru.bilchuk.kinobi.domain.models.Poster
import ru.bilchuk.kinobi.domain.models.Rating
import javax.inject.Inject

class MovieMapperImpl @Inject constructor(): MovieMapper {

    override fun convert(dto: MovieDto): Movie =
        Movie(
            id = dto.id,
            name = dto.name.orEmpty(),
            poster = Poster(
                url = dto.poster?.url,
                previewUrl = dto.poster?.previewUrl,
            ),
            description = dto.description.orEmpty(),
            backdrop = Backdrop(
                url = dto.backdrop?.url,
                previewUrl = dto.backdrop?.previewUrl,
            ),
            rating = Rating(
                kp = dto.rating?.kp
            ),
            genre = dto.genres?.get(0)?.name ?: "",
        )
}