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

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.bilchuk.kinobi.R
import ru.bilchuk.kinobi.databinding.FragmentMovieDetailsBinding
import ru.bilchuk.kinobi.presentation.KinobiApp
import ru.bilchuk.kinobi.presentation.movies.description.Description
import ru.bilchuk.kinobi.presentation.utils.MarginItemDecoration
import ru.bilchuk.kinobi.presentation.utils.format
import ru.bilchuk.kinobi.presentation.utils.hideBottomNav
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: MovieDetailsViewModel

    private val args: MovieDetailsFragmentArgs by navArgs()

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as KinobiApp)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie
        with(binding) {
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener { findNavController().navigateUp() }
                title = movie?.name.orEmpty()
            }
            Glide
                .with(root.context)
                .load(movie?.backdrop?.url ?: movie?.poster?.url)
                .into(image)
            val ratingStr = "${movie?.rating?.kp?.format(1).orEmpty()}★"
            cardDescription.setOnClickListener {
                val direction = MovieDetailsFragmentDirections.actionOpenDescription(
                    Description(
                        toolbarTitle = movie?.name.orEmpty(),
                        title = getString(R.string.title_about_movie),
                        extra = ratingStr,
                        subtitle = movie?.genre.orEmpty(),
                        description = movie?.description.orEmpty(),
                    )
                )
                Navigation.findNavController(it).navigate(direction)
            }
            tvDescription.text = movie?.description.orEmpty()
            tvRating.text = ratingStr
            tvGenre.text = movie?.genre?.replaceFirstChar{ it.uppercase() }.orEmpty()
            btnOpenReviews.setOnClickListener {
                val direction = MovieDetailsFragmentDirections.actionOpenReviews(args.movie)
                Navigation.findNavController(it).navigate(direction)
            }
            bannerList.addItemDecoration(
                MarginItemDecoration(
                    spaceSize = resources.getDimensionPixelSize(R.dimen.default_margin_small),
                    orientation = GridLayoutManager.HORIZONTAL,
                    startMargin = resources.getDimensionPixelSize(R.dimen.default_margin_small),
                )
            )
        }

        getPosterListAsync()
    }

    override fun onStart() {
        super.onStart()
        hideBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPosterListAsync() {
        args.movie?.let {
            viewModel.getPosterList(it.id)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    Log.d("MovieDetailsFragment", "UI update: $it")
                    binding.bannerList.adapter = PostersApadter(
                        poster = it.poster
                    )
                    when {
                        it.isError -> showSnackBar(R.string.general_error)
                    }
                }
            }
        }
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.userMessageShown()
    }
}