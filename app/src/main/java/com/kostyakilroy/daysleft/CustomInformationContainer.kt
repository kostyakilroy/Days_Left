package com.kostyakilroy.daysleft

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class CustomInformationContainer(context: Context,
                     attrs: AttributeSet?,
                     defStyleAttr: Int,
                     defStyleRes: Int
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var informationName: TextView
    private var informationContent: TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_information_container, this, true)
        informationName = view.findViewById(R.id.information_name)
        informationContent = view.findViewById(R.id.information_content)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomInformationContainer,
            defStyleAttr, defStyleRes).apply {

            try {
                val infoName = getString(R.styleable.CustomInformationContainer_informationName)
                setNameText(infoName)
                val infoContent = getString(R.styleable.CustomInformationContainer_informationContent)
                setContentText(infoContent)
            } finally {
                recycle()
            }
        }
    }

    fun setNameText(text: String?) {
        informationName.text = text ?: "Title"
    }

    fun setContentText(text: String?) {
        informationContent.text = text ?: "Content"
    }
}