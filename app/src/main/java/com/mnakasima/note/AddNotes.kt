package com.mnakasima.note

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.lang.Exception

class AddNotes : AppCompatActivity() {

    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        var bundle:Bundle?= intent.extras

        try{
            id=bundle!!.getInt("ID", 0)
            etTitle.setText(bundle.getString("Title"))
            etDes.setText(bundle.getString("Description"))
        }catch(ex:Exception){

        }
    }

    fun buAdd(view: View){

        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", etTitle.text.toString())
        values.put("Description", etDes.text.toString())



        if(id == 0 ) {
            val ID = dbManager.Insert(values)
            if (ID > 0) {
                Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Cannot Add Note", Toast.LENGTH_LONG).show()
            }
        }else{
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.Update(values, "ID=?", selectionArgs)
            if (ID > 0) {
                Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Cannot Add Note", Toast.LENGTH_LONG).show()
            }
        }

    }
}
