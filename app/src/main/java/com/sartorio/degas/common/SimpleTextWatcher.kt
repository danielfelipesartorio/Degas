package com.sartorio.degas.common

import android.text.Editable
import android.text.TextWatcher

open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {
        //no implementation
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //no implementation
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //no implementation
    }
}