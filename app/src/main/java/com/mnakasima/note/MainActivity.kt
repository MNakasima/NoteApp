package com.mnakasima.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = arrayListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadNote()

       var myNotesAdapter = NoteAdapter(listNotes)
        lvNotes.adapter = myNotesAdapter

    }

    fun loadNote(){
        listNotes.add(Note(1,"Study","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"))
        listNotes.add(Note(2,"Study again","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"))
        listNotes.add(Note(3,"Pause and then Study","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"))
    }

    inner class NoteAdapter:BaseAdapter{

        var listNoteAdapter = arrayListOf<Note>()

        constructor(listNoteAdapter:ArrayList<Note>):super(){
            this.listNoteAdapter = listNoteAdapter
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var myNote = listNoteAdapter[position]

            myView.tvTitle.text = myNote.title
            myView.tvDescription.text = myNote.description

            return myView
        }

        override fun getItem(position: Int): Any {
            return listNoteAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNoteAdapter.size
        }



    }

}
