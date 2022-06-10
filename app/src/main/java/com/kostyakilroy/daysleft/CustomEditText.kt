package com.kostyakilroy.daysleft

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

class CustomEditText(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : TextInputLayout(context, attrs, defStyleAttr) {


    private fun updateTextInputBoxState() {


        val hasFocus = isFocused || editText != null && editText!!.hasFocus()
        val isHovered = isHovered || editText != null && editText!!.isHovered

        // Update the text box's stroke color based on the current state.
//        if (!isEnabled) {
//            boxStrokeColor = disabledColor
//        } else if (indicatorViewController.errorShouldBeShown()) {
//            if (strokeErrorColor != null) {
//                updateStrokeErrorColor(hasFocus, isHovered)
//            } else {
//                boxStrokeColor = indicatorViewController.getErrorViewCurrentTextColor()
//            }
//        } else if (counterOverflowed && counterView != null) {
//            if (strokeErrorColor != null) {
//                updateStrokeErrorColor(hasFocus, isHovered)
//            } else {
//                boxStrokeColor = counterView!!.currentTextColor
//            }
//        } else if (hasFocus) {
//            boxStrokeColor = focusedStrokeColor
//        } else if (isHovered) {
//            boxStrokeColor = hoveredStrokeColor
//        } else {
//            boxStrokeColor = defaultStrokeColor
//        }
//    }


    }
}