package com.kyser.mynotes.view

import android.app.Service
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.kyser.mynotes.R
import com.kyser.mynotes.databinding.ActivityMainBinding
import com.kyser.mynotes.view.viewmodel.NoteGridViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_add_note.view.*

class MainActivity : AppCompatActivity(){

    private lateinit var mNoteListModel: NoteGridViewModel
    private lateinit var mainBinding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.toolbar)
        mainBinding.toolbarLayout.title = title
        mainBinding.fab.setOnClickListener { view ->
            getNavController().navigate(R.id.action_noteGrid_to_addNote)
        }
    }
    fun getNavController(): NavController {
        return Navigation.findNavController(mainBinding.content.navHostFragment)
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        getNavController().addOnDestinationChangedListener { controller: NavController, destination: NavDestination, arguments: Bundle? ->
            updateUiElements( controller,  destination )
        }
    }
    private fun updateUiElements(  controller: NavController, destination: NavDestination) {
        if (destination.id == R.id.noteView) {
            mainBinding.fab.visibility = View.GONE
            mainBinding.toolbarLayout.title = "Edit Note"
        } else if (destination.id == R.id.noteGrid) {
            mainBinding.fab.visibility = View.VISIBLE
            mainBinding.toolbarLayout.title  = "All My Notes"
            hideVKB()
        } else if (destination.id == R.id.addNote) {
            mainBinding.fab.visibility = View.GONE
            mainBinding.toolbarLayout.title  = "Add New Note"
        }
    }
    private fun hideVKB() {
        val imm = this.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        if (this.currentFocus != null) imm.hideSoftInputFromWindow( this.currentFocus!!.windowToken, 0 )
    }
}

