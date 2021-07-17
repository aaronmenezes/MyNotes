package com.kyser.mynotes.view.fragment

import com.kyser.mynotes.view.adaptor.NoteGridAdaptor
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kyser.mynotes.R
import com.kyser.mynotes.databinding.FragmentNoteGridBinding
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.util.DataState
import com.kyser.mynotes.view.MainActivity
import com.kyser.mynotes.view.component.SpaceItemDecoration
import com.kyser.mynotes.view.viewmodel.MainStateEvent
import com.kyser.mynotes.view.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Observer

@AndroidEntryPoint
class NoteGrid : Fragment(), NoteGridAdaptor.ItemEvent {

    private val viewModel: NoteViewModel by activityViewModels()
    private lateinit var mNoteGridAdaptor:NoteGridAdaptor
    private lateinit var mNoteGridBinding: FragmentNoteGridBinding


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

         viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
             run {
                 when (dataState) {
                     is DataState.Success<List<Note>> -> {
                         mNoteGridAdaptor.setNoteList(dataState.data)
                     }
                     is DataState.Error -> {
                          Toast.makeText(context,"=error", Toast.LENGTH_SHORT).show()
                     }
                     is DataState.Loading -> {
                         Toast.makeText(context,"=loading", Toast.LENGTH_SHORT).show()
                     }
                 }
             }
         })
        viewModel.setStateEvent(MainStateEvent.GetNotesEvent)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =  NoteGrid()
    }

    override fun onItemClick(itemView: View, note: Note) {
        val bundle = Bundle()
        bundle.putSerializable("note",note)
        bundle.putInt("id", note.id)
        bundle.putString("title", note.name)
        bundle.putString("body", note.body)
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
        viewModel.setStateEvent(MainStateEvent.DeleteNoteEvent,note)
    }
    private fun showPriorityDialog(note: Note) {
        val input = EditText(context)
        input.setText(note.priority.toString() )
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_NUMBER
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.priority_dialog_title))
            .setMessage(resources.getString(R.string.priority_dialog_desc))
            .setView(input)
            .setPositiveButton( resources.getString(R.string.delete_dialog_yes) ) { dialogInterface: DialogInterface, i: Int ->
                var priorty = input.text.toString().toInt()
                if(priorty>5)priorty= 5;
                note.priority=priorty
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
        viewModel.setStateEvent(MainStateEvent.UpdateNotesPriorityEvent,note)
    }

}


