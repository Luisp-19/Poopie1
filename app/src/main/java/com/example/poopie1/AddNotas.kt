package com.example.poopie1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddNotas : AppCompatActivity(){

    private lateinit var editNotaView: EditText
    private lateinit var editSubnotaView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_notas)
        editNotaView = findViewById(R.id.edit1)
        editSubnotaView = findViewById(R.id.edit2)

        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNotaView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else if (TextUtils.isEmpty(editSubnotaView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val notaedit1 = editNotaView.text.toString()
                val subnotaedit1 = editSubnotaView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, notaedit1)
                replyIntent.putExtra(EXTRA1_REPLY, subnotaedit1)
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