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

import ru.bilchuk.kinobi.data.models.network.ReviewDto
import ru.bilchuk.kinobi.domain.models.Review
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ReviewMapperImpl @Inject constructor(): ReviewMapper {

override fun convert(dto: ReviewDto): Review =
    Review(
        title = dto.title,
        review = dto.review,
        date = if (dto.date.isNullOrEmpty()) {
            null
        } else {
            SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            ).parse(dto.date)
        },
        author = dto.author
    )
}