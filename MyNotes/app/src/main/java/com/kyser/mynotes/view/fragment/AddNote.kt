package com.kyser.mynotes.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kyser.mynotes.databinding.FragmentAddNoteBinding
import com.kyser.mynotes.model.service.NotesManager
import java.text.SimpleDateFormat
import java.util.*

class AddNote : Fragment() {

    private lateinit var addNoteBinding: FragmentAddNoteBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        addNoteBinding.back.setOnClickListener { view -> requireActivity().onBackPressed() }
        addNoteBinding.btnSave.setOnClickListener { view -> addNewNote() }
        return addNoteBinding.root
    }
    private fun addNewNote() {
        val s = SimpleDateFormat("ddMMyyyyhhmmss")
        val format = s.format(Date())
        NotesManager.instance.addNewNote(  addNoteBinding.noteTitleInp.text.toString(),  format, "1", addNoteBinding.noteBodyInp.text.toString() )
        requireActivity().onBackPressed()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = AddNote()
    }
}