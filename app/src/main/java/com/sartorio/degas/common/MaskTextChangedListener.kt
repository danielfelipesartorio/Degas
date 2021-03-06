package com.sartorio.degas.common

import android.text.TextUtils
import android.widget.EditText


class MaskTextChangedListener(private val mask: String, private val editText: EditText) :
    SimpleTextWatcher() {
    private var isUpdating: Boolean = false
    private var oldText: String? = ""


    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (this.isUpdating) {
            this.isUpdating = false
        } else {
            var text = removeMask(s!!.toString())
            if (text!!.length > this.oldText!!.length) {
                val maskedText = maskText(this.mask, text)
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
                    maskedText += text!![textCharIndex]
                    textCharIndex++
                    if (textCharIndex >= text.length) {
                        break
                    }
                } else {
                    maskedText += maskCharacter
                }
            }
            return maskedText
        }
    }
}
