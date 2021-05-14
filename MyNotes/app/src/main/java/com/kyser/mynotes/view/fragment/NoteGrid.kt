package com.kyser.mynotes.view.fragment

import NoteGridAdaptor
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kyser.mynotes.R
import com.kyser.mynotes.databinding.FragmentNoteGridBinding
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.model.service.NotesManager
import com.kyser.mynotes.view.MainActivity
import com.kyser.mynotes.view.component.SpaceItemDecoration
import com.kyser.mynotes.view.viewmodel.NoteGridViewModel

class NoteGrid : Fragment(), NoteGridAdaptor.ItemEvent {

    private lateinit var mNoteListModel: NoteGridViewModel
    private lateinit var mNoteGridAdaptor: NoteGridAdaptor
    private lateinit var mNoteGridBinding: FragmentNoteGridBinding

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        mNoteGridBinding = FragmentNoteGridBinding.inflate(inflater, container, false)
        return mNoteGridBinding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNoteGridBinding.noteGrid.layoutManager =  GridLayoutManager(context,2)
        mNoteGridAdaptor = NoteGridAdaptor(requireContext(),this);
        mNoteGridBinding.noteGrid.adapter = mNoteGridAdaptor
        mNoteGridBinding.noteGrid.addItemDecoration( SpaceItemDecoration(20,20,true))
        mNoteListModel = ViewModelProvider(this).get(NoteGridViewModel::class.java)
        mNoteListModel.getNoteList().observe( viewLifecycleOwner, Observer { model->run{
            println(model[0].getBody())
            mNoteGridAdaptor.setNoteList(model)
        } } )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =  NoteGrid()
    }

    override fun onItemClick(itemView: View, note: Note) {
        val bundle = Bundle()
        bundle.putInt("id", note.getId())
        bundle.putString("title", note.getName())
        bundle.putString("note", note.getBody())
        (activity as MainActivity).getNavController().navigate(R.id.action_noteGrid_to_noteView, bundle)
    }

    override fun onItemMenuClick(itemView: View, note: Note) {
        val popupMenu = PopupMenu(context, itemView)
        popupMenu.menuInflater.inflate(R.menu.note_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.itemId == R.id.option_2) showDeleteDialog(note)
            if (menuItem.itemId == R.id.option_1) showPriorityDialog(note)
            popupMenu.dismiss()
            true
        }
        popupMenu.setOnDismissListener { popupMenu: PopupMenu ->{} }
        popupMenu.show()
    }
    private fun showDeleteDialog(note: Note) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_dialog_title))
            .setMessage(resources.getString(R.string.delete_dialog_desc))
            .setPositiveButton(resources.getString(R.string.delete_dialog_yes)) { dialogInterface: DialogInterface, i: Int ->
                deleteNote(note )
            }
            .setNegativeButton( resources.getString(R.string.delete_dialog_no) ) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.setNeutralButton( resources.getString(R.string.delete_dialog_cancel)){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }
    private fun deleteNote(note: Note) {
        NotesManager.instance.deleteNote(note.getId())
        updateNoteList()
    }
    private fun showPriorityDialog(note: Note) {
        val input = EditText(context)
        input.setText(note.getPriority().toString() )
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_NUMBER
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.priority_dialog_title))
            .setMessage(resources.getString(R.string.priority_dialog_desc))
            .setView(input)
            .setPositiveButton( resources.getString(R.string.delete_dialog_yes) ) { dialogInterface: DialogInterface, i: Int ->
                note.setPriority(input.text.toString().toInt())
                updateNotePriority(note)
            }
            .setNegativeButton(resources.getString(R.string.delete_dialog_no)) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .setNeutralButton( resources.getString(R.string.delete_dialog_cancel) ) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }
    private fun updateNotePriority(note: Note) {
        NotesManager.instance.updateNotePriority(note.getId(), note.getPriority())
        updateNoteList()
    }
    private fun updateNoteList() {
        /*mNoteListModel.getNoteList().observe(this, { notes ->
            ( mainBinding.content.noteGrid.adapter as NoteGridAdaptor).setNoteList(notes)
            if (getView() != null) Snackbar.make(
                getView(),
                R.string.snack_bar_note_refresh,
                Snackbar.LENGTH_SHORT
            ).show()
        })*/
    }
}