package com.mnakasima.note

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = arrayListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }

    fun loadQuery(title:String){
        var dbManager = DbManager(this)
        val projection = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projection,"Title like ? ", selectionArgs,"Title")

        if(cursor.moveToFirst()){

            do{
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(Note(ID, title, description))

            }while(cursor.moveToNext())
        }

        var myNotesAdapter = NoteAdapter(this, listNotes)
        lvNotes.adapter = myNotesAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                loadQuery("%"+query+"%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addNote -> {
                var intent = Intent(this, AddNotes::class.java)
                startActivity(intent)
            }

        }

        return super.onOptionsItemSelected(item)
    }

    inner class NoteAdapter:BaseAdapter{

        var listNoteAdapter = arrayListOf<Note>()
        var context:Context?=null

        constructor(context: Context, listNoteAdapter:ArrayList<Note>):super(){
            this.listNoteAdapter = listNoteAdapter
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var myNote = listNoteAdapter[position]

            myView.tvTitle.text = myNote.title
            myView.tvDescription.text = myNote.description

            myView.ivDel.setOnClickListener ( View.OnClickListener{
                var dbManager = DbManager(this.context!!)
                val selectionsArgs = arrayOf(myNote.id.toString())
                dbManager.Delete("ID=?", selectionsArgs)
                loadQuery("%")
            })

            myView.ivEdit.setOnClickListener(View.OnClickListener {

                GoToUpdate(myNote)

            })

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

    fun GoToUpdate(note:Note){
        var intent = Intent(this, AddNotes::class.java)
        intent.putExtra("ID", note.id)
        intent.putExtra("Title", note.title)
        intent.putExtra("Description", note.description)
        startActivity(intent)
    }

}
