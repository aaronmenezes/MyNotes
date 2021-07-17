package com.kyser.mynotes.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.kyser.mynotes.databinding.FragmentNoteViewBinding
import com.kyser.mynotes.model.Note
import com.kyser.mynotes.util.DataState
import com.kyser.mynotes.view.MainActivity
import com.kyser.mynotes.view.viewmodel.MainStateEvent
import com.kyser.mynotes.view.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteView : Fragment(), TextWatcher {

    private lateinit var mNoteModel: Note
    private val viewModel: NoteViewModel by activityViewModels()
    private lateinit var mFragmentBinding: FragmentNoteViewBinding
    private var mTextUpdate = false

    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        mFragmentBinding = FragmentNoteViewBinding.inflate(inflater, container, false)
        return mFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNoteModel = requireArguments().getSerializable("note") as Note
        mFragmentBinding.noteTitleInp.setText(mNoteModel.name)
        mFragmentBinding.noteBodyInp.setText(mNoteModel.body)
        mFragmentBinding.noteTitleInp.addTextChangedListener(this)
        mFragmentBinding.noteBodyInp.addTextChangedListener(this)
        (activity as MainActivity).getMainBinding().toolbar.setNavigationOnClickListener(View.OnClickListener { backPressed() })
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            run {
                when (dataState) {
                    is DataState.Success<List<Note>> -> {
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
    }



    private fun backPressed() {
        if(mTextUpdate)
            viewModel.setStateEvent(MainStateEvent.UpdateNotesEvent,   mNoteModel)
        (activity as MainActivity).getMainBinding().toolbar.setNavigationOnClickListener(null)
        requireActivity().onBackPressed()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =  NoteView()
    }

    override fun afterTextChanged(p0: Editable?) {
        mTextUpdate = !(mFragmentBinding.noteTitleInp.text.toString()
            .equals(mNoteModel.name) && mFragmentBinding.noteBodyInp.text.toString()
            .equals(mNoteModel.body))
        mNoteModel.name = mFragmentBinding.noteTitleInp.text.toString()
        mNoteModel.body = mFragmentBinding.noteBodyInp.text.toString()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
}