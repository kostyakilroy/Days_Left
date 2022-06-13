package com.kostyakilroy.daysleft

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class CustomTextView(context: Context,
                     attrs: AttributeSet?,
                     defStyleAttr: Int,
                     defStyleRes: Int
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var nameTextView: TextView
    private var contentTextView: TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_text_view, this, true)
        nameTextView = view.findViewById(R.id.nameText)
        contentTextView = view.findViewById(R.id.contentText)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditText,
            defStyleAttr, defStyleRes).apply {

                try {
                    val nameText = getString(R.styleable.CustomEditText_nameText)
                    setNameText(nameText)
                    val contentText = getString(R.styleable.CustomEditText_contentText)
                    setContentText(contentText)
                } finally {
                    recycle()
                }
        }
    }

    fun setNameText(text: String?) {
        nameTextView.text = text ?: "Title"
    }

    fun setContentText(text: String?) {
        contentTextView.text = text ?: "Content"
    }
}