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
package ru.bilchuk.kinobi.presentation.movies.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.bilchuk.kinobi.R
import ru.bilchuk.kinobi.databinding.FragmentDescriptionBinding
import ru.bilchuk.kinobi.presentation.utils.format
import ru.bilchuk.kinobi.presentation.utils.hideBottomNav

class DescriptionFragment : Fragment() {

    private val args: DescriptionFragmentArgs by navArgs()

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val description = args.description
        with(binding) {
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener { findNavController().navigateUp() }
                title = description?.toolbarTitle.orEmpty()
            }

            tvTitle.text = description?.title
            tvExtra.text = description?.extra
            tvSubtitle.text = description?.subtitle?.replaceFirstChar{ it.uppercase() }.orEmpty()
            tvDescription.text = description?.description
        }
    }

    override fun onStart() {
        super.onStart()
        hideBottomNav()
    }
}