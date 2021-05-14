package com.kyser.mynotes.view

import android.app.Service
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
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
        mNoteListModel = ViewModelProvider(this).get(NoteGridViewModel::class.java)
    }

    fun getNavController(): NavController {
        return Navigation.findNavController(mainBinding.content.navHostFragment)
    }

    fun getViewModel():NoteGridViewModel{
        return mNoteListModel
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
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
            supportActionBar?.title = "Edit Note"
        } else if (destination.id == R.id.noteGrid) {
            mainBinding.fab.visibility = View.VISIBLE
            hideVKB()
            supportActionBar?.setDisplayHomeAsUpEnabled(false);
            supportActionBar?.setDisplayShowHomeEnabled(false);
            supportActionBar?.title = "All My Notes"
        } else if (destination.id == R.id.addNote) {
            mainBinding.fab.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
            supportActionBar?.title = "Add New Note"
        }
    }
    private fun hideVKB() {
        val imm = this.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        if (this.currentFocus != null) imm.hideSoftInputFromWindow( this.currentFocus!!.windowToken, 0 )
    }

    fun getMainBinding(): ActivityMainBinding {
        return mainBinding
        //mainBinding.toolbar.setNavigationOnClickListener(View.OnClickListener { backPressed })
    }
}

