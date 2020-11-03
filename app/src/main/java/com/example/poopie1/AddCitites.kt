package com.example.poopie1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddCities : AppCompatActivity(){

    private lateinit var editCityView: EditText
    private lateinit var editCountryView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_cities)
        editCityView = findViewById(R.id.edit1)
        editCountryView = findViewById(R.id.edit2)

        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCityView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val cityedit1 = editCityView.text.toString()
                val countryedit1 = editCountryView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, cityedit1)
                replyIntent.putExtra(EXTRA1_REPLY, countryedit1)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val EXTRA1_REPLY = "com.example.android.wordlistsql.REPLY1"
    }
}