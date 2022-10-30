package com.example.recordnotebook.ui.mainscreen

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainScreenItemDecoration(private val verticalSpace: Int, private val horizontalSpace: Int) :
    RecyclerView.ItemDecoration() {
    private val halfSpace: Int = verticalSpace / 2
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        with(outRect) {
            when (parent.getChildAdapterPosition(view)) {
                0 -> {
                    top = verticalSpace
                    bottom = halfSpace
                }
                else -> {
                    top = halfSpace
                    bottom = halfSpace
                }
            }
            left = horizontalSpace
            right = horizontalSpace
        }

    }
}