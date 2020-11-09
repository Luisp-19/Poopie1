package com.example.poopie1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.recyclerview_item.*
import kotlin.math.log


class EditNotas: AppCompatActivity() {

    private lateinit var editNota1View: EditText
    private lateinit var editSubnota1View: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit)
        editNota1View = findViewById(R.id.nota_edit)
        editSubnota1View = findViewById(R.id.subnota_edit)

        val intent = intent
        editNota1View.setText( intent.getStringExtra(EXTRAs_REPLY) )
        editSubnota1View.setText( intent.getStringExtra(EXTRAss_REPLY) )

        val id = intent.getIntExtra( EXTRAid_REPLY, -1 )
        Log.d("test", "$id")
    }

    fun saveNotas (view: View) {
            val replyIntent = Intent()
            if ( TextUtils.isEmpty(editNota1View.text) ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else if ( TextUtils.isEmpty(editSubnota1View.text) ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val nota = editNota1View.text.toString()
                val subnota = editSubnota1View.text.toString()

                val id = intent.getIntExtra( EXTRAid_REPLY, -1 )
                if( id != -1 ) {
                    replyIntent.putExtra(EXTRAid_REPLY, id)
                }
                Log.d("test", "$id")
                replyIntent.putExtra(EXTRAs_REPLY, nota)
                replyIntent.putExtra(EXTRAss_REPLY, subnota)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    companion object {
        const val EXTRAs_REPLY = "com.example.android.wordlistsql.REPLYs"
        const val EXTRAss_REPLY = "com.example.android.wordlistsql.REPLYss"
        const val EXTRAid_REPLY = "com.example.android.wordlistsql.REPLYid"
    }
}