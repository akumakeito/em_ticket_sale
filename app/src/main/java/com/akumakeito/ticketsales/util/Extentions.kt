package com.akumakeito.ticketsales.util

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale


fun Int.toFormattedPriceString(): String = String.format(Locale.FRANCE, "%,d", this)

fun EditText.setInput(saveInput: (String) -> Unit, updateHintVisibility: () -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            updateHintVisibility()
            if (before != count) {
                saveInput(s.toString())
            }

        }

        override fun afterTextChanged(s: Editable?) {

            Log.d("Destinations", "afterTextChanged:  ${s.toString()}")

            setSelection(s?.length ?: 0)
        }
    })
}

fun EditText.updateHintVisibility(
    textInputContainer: TextInputLayout,
    @StringRes hint: Int,
    resources: Resources
) {
    textInputContainer.hint =
        if (this.hasFocus() || this.text.isNotEmpty()) null else resources.getString(hint)
}
