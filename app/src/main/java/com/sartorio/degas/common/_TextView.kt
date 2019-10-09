package com.sartorio.degas.common

import android.widget.TextView
import java.lang.NumberFormatException

fun TextView.addOne() {
    try {
        this.text = (this.text.toString().toInt() + 1).toString()
    } catch (error: NumberFormatException) {
        this.text = "1"
    }
}

fun TextView.removeOne() {
    try {
        val temp = this.text.toString().toInt() - 1
        if (temp >= 0) {
            this.text = temp.toString()
        } else {
            this.text = "0"
        }

    } catch (error: NumberFormatException) {
        this.text = "0"
    }
}