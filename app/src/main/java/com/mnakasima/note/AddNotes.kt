package com.mnakasima.note

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
    }

    fun buAdd(view: View){

        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", etTitle.text.toString())
        values.put("Description", etDes.text.toString())

        val ID = dbManager.Insert(values)

        if(ID>0){
            Toast.makeText(this,"Note added", Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this,"Cannot Add Note", Toast.LENGTH_LONG).show()
        }

    }
}
