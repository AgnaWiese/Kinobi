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
package ru.bilchuk.kinobi.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val spaceSize: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL,
    private val startMargin: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) = with(outRect) {
        if (orientation == GridLayoutManager.VERTICAL) {
            if (parent.getChildAdapterPosition(view) < spanCount) {
                top = spaceSize
            }
            if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                left = spaceSize
            }
        } else {
            if (parent.getChildAdapterPosition(view) < spanCount) {
                left = spaceSize + startMargin
            }
            if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                top = spaceSize
            }
        }

        right = spaceSize
        bottom = spaceSize
    }
}