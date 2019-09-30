package com.sartorio.degas.ui.customcompoents

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.sartorio.degas.R

class SearchableDialog(
    context: Context,
    itemList: MutableList<String>,
    listener: SearchableDialogClickListener
) : Dialog(context) {

    private val list: ListView
    private var filterText: EditText? = null
    internal lateinit var adapter: ArrayAdapter<String>

    private val filterTextWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int,
            count: Int
        ) {
            adapter.filter.filter(s)
        }
    }

    init {

        setContentView(R.layout.searchble_list_dialog)
        filterText = findViewById<View>(R.id.EditBox) as EditText
        filterText!!.addTextChangedListener(filterTextWatcher)
        list = findViewById<View>(R.id.List) as ListView
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, itemList)
        list.adapter = adapter
        list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            listener.onListItemClick(list.getItemAtPosition(position).toString())
        }
    }

    public override fun onStop() {
        filterText!!.removeTextChangedListener(filterTextWatcher)
    }
}
