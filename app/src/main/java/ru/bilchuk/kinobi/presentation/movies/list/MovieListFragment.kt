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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.bilchuk.kinobi.R
import ru.bilchuk.kinobi.databinding.FragmentMovieListBinding
import ru.bilchuk.kinobi.presentation.KinobiApp
import ru.bilchuk.kinobi.presentation.utils.MarginItemDecoration
import ru.bilchuk.kinobi.presentation.utils.showBottomNav
import javax.inject.Inject

class MovieListFragment : Fragment() {

    @Inject
    lateinit var viewModel: MovieListViewModel

    private var _binding: FragmentMovieListBinding? = null
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
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBottomNav()

        binding.rvMovieList.apply {
            addItemDecoration(
                MarginItemDecoration(
                    spaceSize = resources.getDimensionPixelSize(R.dimen.default_margin),
                    spanCount = resources.getInteger(R.integer.movies_list_span_count)
                )
            )
            addOnScrollListener(createOnScrollListener())
            adapter = MoviesAdapter()
        }

        subscribe()

        if (savedInstanceState == null) {
            viewModel.getMovieListFirstPage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    Log.d("MovieListFragment", "UI update: $it")
                    when {
                        it.isError -> showSnackBar(R.string.general_error)
                    }

                    if (it.pagesLoaded == 1) {
                        binding.rvMovieList.adapter = MoviesAdapter(it.movies.toMutableList())
                    } else if (it.pagesLoaded > 1 && !it.moviesNextChunk.isNullOrEmpty()) {
                        (binding.rvMovieList.adapter as MoviesAdapter).addItems(it.moviesNextChunk)
                    }

                    binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.userMessageShown()
    }

    private fun createOnScrollListener(): RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleThreshold = 2

                val layoutManager = (recyclerView.layoutManager as GridLayoutManager)
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val currentTotalCount = layoutManager.itemCount

                if (currentTotalCount <= lastItem + visibleThreshold) {
                    viewModel.getMovieListNextPage()
                }
            }
        }
    }
}