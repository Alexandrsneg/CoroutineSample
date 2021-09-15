package com.example.coroutinesample.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.coroutinesample.databinding.UniversityItemViewBinding

class UniversityItemView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: UniversityItemViewBinding? = UniversityItemViewBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding?.root)
    }

    fun bind(universityName: String?) {
        universityName?.let {
            binding?.tvName?.text = it
        }
    }

    override fun onDetachedFromWindow() {
        binding = null
        super.onDetachedFromWindow()
    }
}