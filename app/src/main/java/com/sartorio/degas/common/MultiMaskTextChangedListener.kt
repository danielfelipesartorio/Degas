package com.sartorio.degas.common

import android.text.TextUtils
import android.widget.EditText


class MultiMaskTextChangedListener(private val mask1: String, private val mask2: String, private val editText: EditText) :
    SimpleTextWatcher() {
    private var isUpdating: Boolean = false
    private var oldText: String? = ""


    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (this.isUpdating) {
            this.isUpdating = false
        } else {
            val lesserMask = if (mask1.length <= mask2.length)  mask1 else mask2
            val graterMask = if (mask1.length > mask2.length)  mask1 else mask2
            var text = removeMask(s!!.toString())
            if (text!!.length > this.oldText!!.length) {
                val maskedText = if (s.length <= lesserMask.length) maskText(lesserMask, removeMask(text)) else  maskText(graterMask,  removeMask(text))
                text = removeMask(maskedText)
                this.isUpdating = true
                this.editText.setText(maskedText)
                this.editText.setSelection(maskedText!!.length)
            }
            this.oldText = text
        }
    }

    companion object {

        fun removeMask(text: String?): String? {
            return text?.replace("[.]".toRegex(), "")?.replace("[-]".toRegex(), "")
                ?.replace("[/]".toRegex(), "")?.replace("[(]".toRegex(), "")
                ?.replace("[)]".toRegex(), "")?.replace("[ ]".toRegex(), "")
                ?.replace("[+]".toRegex(), "")
        }

        fun applyMask(mask: String, text: String?): String? {
            return if (text == null) null else maskText(mask, removeMask(text))
        }

        private fun maskText(mask: String, text: String?): String? {
            if (TextUtils.isEmpty(text)) {
                return text
            }
            var maskedText = ""
            var textCharIndex = 0
            val maskCharacters = mask.toCharArray()
            val maskLength = maskCharacters.size
            for (maskCharIndex in 0 until maskLength) {
                val maskCharacter = maskCharacters[maskCharIndex]
                if (maskCharacter == '#') {
                    maskedText = maskedText + text!![textCharIndex]
                    textCharIndex++
                    if (textCharIndex >= text.length) {
                        break
                    }
                } else {
                    maskedText = maskedText + maskCharacter
                }
            }
            return maskedText
        }
    }
}
