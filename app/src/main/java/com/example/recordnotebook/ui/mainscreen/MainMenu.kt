package com.example.recordnotebook.ui.mainscreen

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.recordnotebook.R
import com.example.recordnotebook.databinding.ItemMenuBinding
import com.example.recordnotebook.utils.dp

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

        showAsDropDown(view, 0, 8.dp.toInt(), Gravity.END)
    }

    fun addElement(iconDrawable: Int? = null, name: Int, listener: () -> Unit): MainMenu {

        val binding = ItemMenuBinding.inflate(LayoutInflater.from(context.requireContext()))
        binding.tvTittle.setText(name)
        binding.tvTittle.visibility = View.VISIBLE

        iconDrawable?.let {
            val drawable = ContextCompat.getDrawable(context.requireContext(), iconDrawable)
            binding.ivIcon.setImageDrawable(drawable)
            binding.ivIcon.visibility = View.VISIBLE
        }
        binding.root.setOnClickListener {
            listener.invoke()
            this.dismiss()
        }

        parentView.addView(binding.root)
        return this
    }
}