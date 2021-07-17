package com.kyser.mynotes.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kyser.mynotes.databinding.FragmentAddNoteBinding
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.util.DataState
import com.kyser.mynotes.view.MainActivity
import com.kyser.mynotes.view.viewmodel.MainStateEvent
import com.kyser.mynotes.view.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

@AndroidEntryPoint
class AddNote : Fragment() {

    private val viewModel: NoteViewModel by activityViewModels()
    private lateinit var addNoteBinding: FragmentAddNoteBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        (activity as MainActivity).getMainBinding().toolbar.setNavigationOnClickListener(View.OnClickListener {requireActivity().onBackPressed()})
        addNoteBinding.btnSave.setOnClickListener { view -> addNewNote() }
        return addNoteBinding.root
    }
    private fun addNewNote() {
        val s = SimpleDateFormat("ddMMyyyyhhmmss")
        val format = s.format(Date())
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            run {
                when (dataState) {
                    is DataState.Success<List<Note>> -> {
                        requireActivity().onBackPressed()
                        Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()
                    }
                    is DataState.Error -> {
                        Toast.makeText(context,"error", Toast.LENGTH_SHORT).show()
                    }
                    is DataState.Loading -> {
                        Toast.makeText(context,"loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        viewModel.setStateEvent(MainStateEvent.AddNotesEvent, addNoteBinding.noteTitleInp.text.toString(),  format, "1", addNoteBinding.noteBodyInp.text.toString() )
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = AddNote()
    }
}