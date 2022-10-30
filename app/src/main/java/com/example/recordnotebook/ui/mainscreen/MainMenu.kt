package com.example.recordnotebook.ui.mainscreen

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.recordnotebook.R
import com.example.recordnotebook.utils.dp
import kotlin.math.roundToInt

class MainMenu(val context: Fragment) : PopupWindow() {
    private val parentView: LinearLayout =
        context.layoutInflater.inflate(R.layout.menu_main, null) as LinearLayout

    init {
        contentView = parentView
        width = LinearLayout.LayoutParams.WRAP_CONTENT // check this
        height = LinearLayout.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isClippingEnabled = false
    }

    fun showAtRight(view: View) {
        parentView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        width = parentView.measuredWidth
        height = parentView.measuredHeight
        contentView = parentView

        showAsDropDown(view, 0, 0, Gravity.END)
    }

    fun addElement(name: Int, listener: () -> Unit): MainMenu {
        val text = TextView(context.requireContext())
        text.setText(name)
        text.setTextColor(Color.parseColor("#ffffff"))

        text.setOnClickListener {
            listener.invoke()
            this.dismiss()
        }
        parentView.addView(text)
        return this
    }
}