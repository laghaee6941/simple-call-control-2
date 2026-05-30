package com.simple.callcontrol

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.*

class MainActivity : Activity() {

    lateinit var listView: ListView
    lateinit var editText: EditText
    lateinit var button: Button

    var numbers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        editText = EditText(this)
        editText.hint = "Enter allowed number"

        button = Button(this)
        button.text = "Add"

        listView = ListView(this)

        layout.addView(editText)
        layout.addView(button)
        layout.addView(listView)

        setContentView(layout)

        loadNumbers()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
        listView.adapter = adapter

        button.setOnClickListener {
            val num = editText.text.toString()
            if (num.isNotEmpty()) {
                numbers.add(num)
                saveNumbers()
                adapter.notifyDataSetChanged()
                editText.text.clear()
            }
        }

        listView.setOnItemLongClickListener { _, _, pos, _ ->
            numbers.removeAt(pos)
            saveNumbers()
            adapter.notifyDataSetChanged()
            true
        }
    }

    private fun saveNumbers() {
        val prefs = getSharedPreferences("call_prefs", Context.MODE_PRIVATE)
        prefs.edit().putStringSet("numbers", numbers.toSet()).apply()
    }

    private fun loadNumbers() {
        val prefs = getSharedPreferences("call_prefs", Context.MODE_PRIVATE)
        val set = prefs.getStringSet("numbers", emptySet())
        numbers.clear()
        numbers.addAll(set ?: emptySet())
    }
}
