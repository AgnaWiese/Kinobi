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
package ru.bilchuk.kinobi.presentation.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.bilchuk.kinobi.presentation.utils.showBottomNav
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.bilchuk.kinobi.R
import ru.bilchuk.kinobi.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBottomNav()

        with(binding) {
            toolbar.title = getString(R.string.title_more)
            itemViewCode.setOnClickListener { showSourceCode() }
            itemAbout.setOnClickListener { showAboutDialog() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSourceCode() =
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getString(R.string.url_project_github))
        })

    private fun showAboutDialog() =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.about_kinobi)
            .setMessage(R.string.about_kinobi_message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
}