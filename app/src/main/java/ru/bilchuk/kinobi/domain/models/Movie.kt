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
package ru.bilchuk.kinobi.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Movie(
    val id: Int,
    val name: String = "",
    val poster: Poster?,
    val description: String = "",
    val backdrop: Backdrop?,
    val rating: Rating?,
    val genre: String = "",
) : Parcelable

@Parcelize
data class Poster(
    val url: String?,
    val previewUrl: String?,
) : Parcelable

@Parcelize
data class Backdrop(
    val url: String?,
    val previewUrl: String?,
) : Parcelable

@Parcelize
data class Rating(
    val kp: Double?,
) : Parcelable

@Parcelize
data class Review(
    val title: String?,
    val review: String?,
    val date: Date?,
    val author: String?,
) : Parcelable