package com.example.poopie1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddCities : AppCompatActivity(){

    private lateinit var editWordView: EditText
    private lateinit var editnumberView: EditText

    public override fun OnCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_cities)
        editWordView = findViewById(R.id.edit1))
        editnumberView = findViewById(R.id.edit2/))

        val button = findViewById<Button>(R.id./*adcionar id*/)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val edit1 = editWordView.text.toString()
                val edit2 = editnumberView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, edit1)
                replyIntent.putExtra(EXTRA1_REPLY, edit2)
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