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
package ru.bilchuk.kinobi.presentation.movies.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.bilchuk.kinobi.databinding.ListItemPosterBinding
import ru.bilchuk.kinobi.domain.models.Poster

internal class PostersApadter(private val poster: List<Poster>) :
    RecyclerView.Adapter<PostersApadter.PosterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder =
        PosterViewHolder(
            ListItemPosterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) =
        holder.bindView(poster[position])

    override fun getItemCount(): Int = poster.size

    internal class PosterViewHolder(private val binding: ListItemPosterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(poster: Poster) {
            with(binding) {
                Glide
                    .with(root.context)
                    .load(poster.url)
                    .into(imgPreview)
            }
        }
    }
}