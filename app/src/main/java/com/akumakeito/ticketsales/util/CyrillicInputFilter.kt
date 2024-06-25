package com.akumakeito.ticketsales.util

import android.text.InputFilter
import android.text.Spanned

class CyrillicInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) return null
        val filtered = source.filter { it.isCyrillic() }
        return filtered.ifEmpty {
            ""
        }
    }

    private fun Char.isCyrillic(): Boolean {
        return this in '\u0400'..'\u04FF' || this in '\u0500'..'\u052F' || this == ' '

    }
}