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
package ru.bilchuk.kinobi.presentation.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.bilchuk.kinobi.databinding.ListItemMovieBinding
import ru.bilchuk.kinobi.domain.models.Movie

internal class MoviesAdapter(private val movies: MutableList<Movie> = mutableListOf()) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindView(movies[position])

    override fun getItemCount(): Int = movies.size

    fun addItems(items: List<Movie>) {
        val startPosition = movies.size
        movies.addAll(items)
        notifyItemRangeInserted(startPosition, items.size)
    }

    internal class MovieViewHolder(private val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(movie: Movie) {
            with(binding) {
                tvTitle.text = movie.name
                tvGenre.text = movie.genre.replaceFirstChar { it.uppercase() }
                Glide
                    .with(root.context)
                    .load(movie.poster?.previewUrl)
                    .into(imgPreview)
                itemCard.setOnClickListener {
                    val direction = MovieListFragmentDirections.actionOpenMovie(movie)
                    Navigation.findNavController(it).navigate(direction)
                }
            }
        }
    }
}